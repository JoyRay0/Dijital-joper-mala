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

final class Notification{

    private string $CACHE_KEY = "notification";

    public function notification(Request $request, Response $response){
    
        $date = (!empty($request->getParsedBody())) ? $request->getParsedBody() : [];

        if(!is_array($date) || empty($date['date']) || !is_string($date['date'])){
            
            log::writeNormalLog("Notification_Date", "Notification date empty");

            return ResHelper::errorResponse(
                $response,
                https::BadRequest->value
            );

        }

        $currentDate = new DateTime('today');
        $requestDate = DateTime::createFromFormat('!d-m-Y', $date['date']);
    
        if(!$requestDate || $requestDate->format('Y-m-d') !== $currentDate->format('Y-m-d')){

            log::writeNormalLog("Notification_Date", "Server date and request date not matching");

            return ResHelper::errorResponse(
                $response,
                https::BadRequest->value
            );

        }

        $cache = Cache::getArrayCache($this->CACHE_KEY);

        $finalCache = $this->finalNotificationResponse($cache);

        if(!empty($finalCache)){

            return ResHelper::jsonResponse(
                $response,
                https::Success->value,
                [
                    "status" => status::Success->value,
                    "from" => "Cache",
                    "data" => $finalCache
            ]);

        }


        try{

            $db = new NotificationDatabase();

            $result = $db->getNotification();

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

            Cache::setArrayCache($this->CACHE_KEY, $result, 300);

            $finalResult = $this->finalNotificationResponse($result);

            return ResHelper::jsonResponse(
                    $response,
                    https::Success->value,
                    [
                        "status" => status::Success->value,
                        "from" => "database",
                        "data" => $finalResult
                    ]
            );

        } catch(PDOException $e){

            log::writeErrorLog($e->getMessage());

            return ResHelper::errorResponse(
                $response,
                https::ServerError->value
            );

        }
    
    }

    private function finalNotificationResponse(array $data) : array{

        $finalResult = [];

        $today = date('Y-m-d');

        if(!empty($data)){

            foreach ($data as $item){

                if(!empty($item) && 
                $today >= $item['start_at'] &&
                $today <= $item['end_at']
                ){

                    $finalResult[] = $item;

                }

            }//loop

        }

        return $finalResult;

    }

}