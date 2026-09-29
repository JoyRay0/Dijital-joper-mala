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

        if(empty($trackerData)){

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

        }catch(PDOException $e){

            Log::writeErrorLog($e->getMessage());

            return ResHelper::errorResponse(
                $response,
                Https::ServerError->value
            );

        }
    
    }

}