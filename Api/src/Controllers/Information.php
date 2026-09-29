<?php

namespace App\Controllers;

use PDOException;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Src\Database\Mysql as Database;
use Src\Helper\CacheHelper as Cache;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\LogHelper as Log;
use Src\Helper\ResponseHelper as ResHelper;
use Src\Helper\StatusHelper as Status;

class Information{

    private const string CACHE_KEY = "information_cache";
    private const string LOCK_KEY = "information_lock";

    public function information(Request $request, Response $response){
    
        $cache = Cache::getArrayCache(self::CACHE_KEY);

        if(!empty($cache)){

            return ResHelper::jsonResponse(
                $response,
                Https::Success->value,
                [
                    "status" => Status::Success->value,
                    "from" => "Cache",
                    "data" => $cache
                ]
            );

        }

        /*
        *
        * Try to DB Access
        *
        * If another request is already loading the DB, wait until its cache become available
        *
        */

        for($i = 0; $i < 10; $i++){

            //Is another request loading?

            if(!empty(Cache::getStringCache(self::LOCK_KEY))){

                /*
                *
                * 1 milli seconds = 1000 micro seconds
                *
                * 100 milli seconds = 100,000 micor seconds
                *
                */

                usleep(100000);

                $cache = Cache::getArrayCache(self::CACHE_KEY);

                if(!empty($cache)){

                    return ResHelper::jsonResponse(
                        $response,
                        Https::Success->value,
                        [
                            "status" => Status::Success->value,
                            "from" => "Cache",
                            "data" => $cache
                        ]
                    );

                }

                continue;

            }

            // No lock -> try to create one

            Cache::setStringCache(self::LOCK_KEY, "lock", 5);

            /*
            * 
            * Double check cache
            *
            * Another request may be created the cache just before we aequired the lock.
            */

            $cache = Cache::getArrayCache(self::CACHE_KEY);

            if(!empty($cache)){

                Cache::deleteStringCache(self::LOCK_KEY);

                return ResHelper::jsonResponse(
                    $response,
                    Https::Success->value,
                    [
                        "status" => Status::Success->value,
                        "from" => "Cache",
                        "data" => $cache
                    ]
                );

            }

            break;

        }//loop

        try{

            $db = new Database();

            $result = $db->getAllInformation();

            if(empty($result)){

                Log::writeNormalLog(
                    "Information Database",
                    "Empty result found"
                );

                return ResHelper::jsonResponse(
                    $response,
                    Https::Success->value,
                    [
                        "status" => Status::Success->value,
                        "from" => "database",
                        "data" => []
                    ]
                );

            }

            Cache::setArrayCache(self::CACHE_KEY, $result, 300);

            return ResHelper::jsonResponse(
                $response,
                Https::Success->value,
                [
                    "status" => Status::Success->value,
                    "from" => "database",
                    "data" => $result
                ]
            );

        }catch(PDOException $e){

            Log::writeErrorLog($e->getMessage());

            return ResHelper::errorResponse(
                $response,
                Https::ServerError->value
            );

        }finally{

            Cache::deleteStringCache(self::LOCK_KEY);

        }
    
    }

}