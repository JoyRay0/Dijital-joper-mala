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

class Tracker{

    public function tracker(Request $request, Response $response){
    
        $trackerData = (!empty($request->getParsedBody())) ? $request->getParsedBody() : [];

        if(empty($trackerData) && (
            empty($trackerData['device_id']) ||
            empty($trackerData['android_version']) ||
            empty($trackerData['sdk_version']) ||
            empty($trackerData['country_code']) ||
            empty($trackerData['last_open']))
        ){

            Log::writeNormalLog(
                "Tracker body",
                "Empty tracker body"
            );

            return ResHelper::errorResponse(
                $response,
                Https::BadRequest->value
            );

        }

        try{

            $db = new Database();

            $db->insertOneDeviceData(
                $trackerData['device_id'],
                $trackerData['android_version'],
                $trackerData['sdk_version'],
                $trackerData['country_code'],
                $trackerData['last_open']
            );

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