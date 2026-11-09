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

class Mantra{

    private string $CACHE_KEY = "mantra_cache";

    public function mantra(Request $request, Response $response){
    
        $cache = Cache::getArrayCache($this->CACHE_KEY);

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

        try{

            $db = new Database();

            $result = $db->getAllMantra();

            if(empty($result)){

                Log::writeNormalLog(
                    "Mantra Database",
                    "Empty mantra database"
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

            Cache::setArrayCache($this->CACHE_KEY, $result, 300);

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

        }
    
    }

}