<?php

namespace App\Controllers;


use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Src\Helper\StatusHelper;
use Src\Helper\ResponseHelper;
use Src\Helper\HttpCodesHelper;
use Src\Helper\LogHelper;

class AppUpdate{

    public function App_update(Request $request, Response $response){
    
        $old_version = 2.4;
        $new_version = 2.5;

        if($new_version > $old_version){

            return ResponseHelper::jsonResponse(
                $response,
                HttpCodesHelper::Success->value,
                [
                    "status" => StatusHelper::Success->value,
                    "version" => (string) $new_version
                ]
            );
        
        
        }else{

            LogHelper::writeNormalLog(
                "Version",
                "no new version = $old_version"
            );

            return ResponseHelper::jsonResponse(
                $response,
                0,
                [
                    "status" => StatusHelper::Failed->value
                ]
            );

        }
    
    }

}