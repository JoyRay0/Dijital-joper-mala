<?php

namespace App\Controllers;

use DateTime;
use PDOException;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Src\Database\SQLite as NotificationDatabase;
use Src\Helper\CacheHelper as Cache;
use Src\Helper\ResponseHelper as ResHelper;
use Src\Helper\HttpCodesHelper as https;
use Src\Helper\StatusHelper as status;
use Src\Helper\LogHelper as log;

class Notification{

    private const string CACHE_KEY = "notification";
    private const string LOCK_KEY = "notification_lock";

    public function notification(Request $request, Response $response){
    
        $date = (!empty($request->getParsedBody())) ? $request->getParsedBody() : [];

        if(!is_array($date) || empty($date['date'])){
            
            log::writeNormalLog("Date", "Notification date empty");

            return ResHelper::errorResponse(
                $response,
                https::BadRequest->value
            );

        }

        $currentDate = new DateTime();

        if(DateTime::createFromFormat('d-m-Y', $date['date']) != $currentDate){

            log::writeNormalLog("Date", "Server date and request date not matching");

            return ResHelper::errorResponse(
                $response,
                https::BadRequest->value
            );

        }

        $cache = Cache::getArrayCache(self::CACHE_KEY);

        if(!empty($cache) && 
        $currentDate >= DateTime::createFromFormat('d-m-Y', $cache['start_at'])  && 
        $currentDate <= DateTime::createFromFormat('d-m-Y', $cache['end_at'])
        ){

            return ResHelper::jsonResponse(
                $response,
                https::Success->value,
                [
                    "status" => status::Success->value,
                    "from" => "Cache",
                    "data" => $cache
                ]);

        }

        /* 
        * Try to get DB access
        *
        * If another request is already loading the DB, wait until its cache becomes available.
        *
        */

        for($i = 0; $i < 50; $i++){

            // Is another request loading cache?

            if(!empty(Cache::getStringCache(self::LOCK_KEY))){

                /*

                * 1 milliseconds = 1000 microseconds
                * 100 milliseconds = 100,000 microseconds

                */

                usleep(100000); 


                $cache = Cache::getArrayCache(self::CACHE_KEY);

                if(!empty($cache) && 
                $currentDate >= DateTime::createFromFormat('d-m-Y', $cache['start_at']) && 
                $currentDate <= DateTime::createFromFormat('d-m-Y', $cache['end_at'])
                ){

                    return ResHelper::jsonResponse(
                        $response,
                        https::Success->value,
                        [
                            "status" => status::Success->value,
                            "from" => "Cache",
                            "data" => $cache
                        ]
                    );

                }

                continue;

            }

            // No lock -> try create one

            Cache::setStringCache(self::LOCK_KEY, "lock", 5);

            /*
            * Double check cache
            *
            * Another request may have created cache just before we acquired the lock.
            *
            */

            $cache = Cache::getArrayCache(self::CACHE_KEY);

            if(!empty($cache) && 
            $currentDate >= DateTime::createFromFormat('d-m-Y', $cache['start_at']) && 
            $currentDate <= DateTime::createFromFormat('d-m-Y', $cache['end_at'])
            ){

                Cache::deleteStringCache(self::LOCK_KEY);

                return ResHelper::jsonResponse(
                    $response,
                    https::Success->value,
                    [
                        "status" => status::Success->value,
                        "from" => "Cache",
                        "data" => $cache
                    ]
                );

            }

            break;

        }//loop

        try{

            $db = new NotificationDatabase();
        
            $result = $db->getSingleNotification($date['date']);

            if(empty($result)){

                log::writeNormalLog("Notification Database", "Empty result from notification database via this date >> {$date['date']}");

                return ResHelper::jsonResponse(
                    $response,
                    https::Success->value,
                    [
                        "status" => status::Success->value,
                        "from" => "database",
                        "data" => []
                    ]
                );

            }

            Cache::setArrayCache(self::CACHE_KEY, $result, 120);

            return ResHelper::jsonResponse(
                    $response,
                    https::Success->value,
                    [
                        "status" => status::Success->value,
                        "from" => "database",
                        "data" => $result
                    ]
            );

        } catch(PDOException $e){

            log::writeErrorLog($e->getMessage());

            return ResHelper::errorResponse(
                $response,
                https::ServerError->value
            );

        }finally{

            Cache::deleteStringCache(self::LOCK_KEY);

        }

    
    }

}