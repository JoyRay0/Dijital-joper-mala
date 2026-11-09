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

class TrackerDTO{

    public ?string $deviceId = null;
    public ?string $androidVersion = null;
    public ?string $sdkVersion = null;
    public ?string $countryCode = null;
    public ?string $lastOpen = null;
    public ?array $activityData = null;

}

class ActivityDataDTO{

    public ?string $activity = null;
    public ?string $duration = null;

}

class Tracker{

    public function tracker(Request $request, Response $response){
    
        $requiredFields = [
            "device_id" => "string", 
            "android_version" => "string", 
            "sdk_version" => "string", 
            "country_code" => "string", 
            "last_open" => "string",
            "activity_data" => "array"
        ];

        $requiredActivityFiled = ["activity", "duration"];

        $trackerData = (!empty($request->getParsedBody())) ? $request->getParsedBody() : [];

        /* Tracker body check */

        foreach ($requiredFields as $field => $type){

            if(!isset($trackerData[$field]) || 
            get_debug_type($trackerData[$field]) !== $type
            ){

                return ResHelper::errorResponse(
                    $response,
                    Https::BadRequest->value
                );

            }

        }//loop

        /* Activity data body check */

        foreach ($trackerData["activity_data"] as $activityData){

            foreach ($requiredActivityFiled as $activityFiled){

                if(!isset($activityData[$activityFiled]) || !is_string($activityData[$activityFiled])){

                    return ResHelper::errorResponse(
                        $response,
                        Https::BadRequest->value
                    );

                }

            }//loop

        }//loop

        $tracker_dto = new TrackerDTO();

        $tracker_dto->deviceId = strip_tags(trim($trackerData["device_id"]));
        $tracker_dto->androidVersion = strip_tags(trim($trackerData["android_version"]));
        $tracker_dto->sdkVersion = strip_tags(trim($trackerData["sdk_version"]));
        $tracker_dto->countryCode = strip_tags(trim($trackerData["country_code"]));
        $tracker_dto->lastOpen = strip_tags(trim($trackerData["last_open"]));
        $tracker_dto->activityData = [];

        foreach ($trackerData["activity_data"] as $_activity){

            $activity_data = new ActivityDataDTO();

            $activity_data->activity = $_activity[$requiredActivityFiled[0]];
            $activity_data->duration = $_activity[$requiredActivityFiled[1]];

            if(!preg_match("/^[a-zA-Z0-9_]+$/", $_activity[$requiredActivityFiled[0]]) ||
            !preg_match("/^[0-9]+$/", $_activity[$requiredActivityFiled[1]])
            ){

                return ResHelper::errorResponse(
                    $response,
                    Https::BadRequest->value
                );

            }else{

                $tracker_dto->activityData[] = $activity_data;

            }

        };

        try{

            $db = new Database();

            /* Insert device information */

            $db->insertOneDeviceData(
                $tracker_dto->deviceId,
                $tracker_dto->androidVersion,
                $tracker_dto->sdkVersion,
                $tracker_dto->countryCode,
                $tracker_dto->lastOpen
            );

            /* Insert activity data */

            foreach ($tracker_dto->activityData as $activity){

                $db->insertOneActivity($activity[$requiredActivityFiled[0]], $activity[$requiredActivityFiled[1]]);

            }//loop

            return ResHelper::jsonResponse(
                $response,
                Https::Success->value,
                [
                    "status" => Status::Success->value
                ]
            );

        }catch(PDOException $e){

            Log::writeErrorLog($e->getMessage());

            return ResHelper::errorResponse(
                $response,
                Https::ServerError->value
            );

        }
    
    }

}