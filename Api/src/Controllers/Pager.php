<?php

namespace App\Controllers;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\LogHelper as Log;
use Src\Helper\ResponseHelper as ResHelper;
use Src\Helper\StatusHelper as Status;
use DateTime;

class Pager{

    public function pager(Request $request, Response $response){
    
        $pagerList = json_decode(file_get_contents( __DIR__ . '../Json/pager.json'), true);

        if(empty($pagerList)){

            Log::writeNormalLog("Pager", "No pager data found");

            return ResHelper::errorResponse(
                $response,
                Https::NotFound->value
            );

        }

        $result = [];
        $currentDate = new DateTime();

        foreach($pagerList as $pager){

            $startAt = DateTime::createFromFormat('d-m-Y', $pager['start_at']);
            $endAt = DateTime::createFromFormat('d-m-Y', $pager['end_at']);

            if($currentDate >= $startAt && 
            $currentDate <= $endAt
            ){

                $result[] = $pager;

            }

        }//loop

        return ResHelper::jsonResponse(
            $response,
            Https::Success->value,
            [
                "status" => Status::Success->value,
                "data" => $result
            ]
        );
    
    }

}