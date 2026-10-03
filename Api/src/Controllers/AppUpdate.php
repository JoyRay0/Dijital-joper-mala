<?php

namespace App\Controllers;


use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Src\Helper\StatusHelper as Status;
use Src\Helper\ResponseHelper as ResHelper;
use Src\Helper\HttpCodesHelper as Https ;
use Src\Helper\LogHelper as Log;

class AppUpdate{

    public function App_update(Request $request, Response $response){
    
        $notification = json_decode(file_get_contents(__DIR__ . '../Json/notification.json'), true);

        if(empty($notification)){

            Log::writeNormalLog("Notification Json", "Empty notification json");

            return ResHelper::errorResponse(
                $response,
                Https::NotFound->value
            );

        }

        $old_version = (float) $notification['old_version'];
        $new_version = (float) $notification['new_version'];

        if($new_version > $old_version){

            return ResHelper::jsonResponse(
                $response,
                Https::Success->value,
                [
                    "status" => Status::Success->value,
                    "version" => (string) $new_version
                ]
            );
        
        
        }else{

            Log::writeNormalLog(
                "Version",
                "no new version = $old_version"
            );

            return ResHelper::jsonResponse(
                $response,
                Https::NotFound->value,
                [
                    "status" => Status::Failed->value
                ]
            );

        }
    
    }

}