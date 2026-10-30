<?php

namespace Src\Helper;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;

class ResponseHelper{

    public static function jsonResponse(Response $response, int $status, array $data) : Response{

        $response = $response
            ->withStatus($status);
            
        $response->getBody()->write(
            json_encode($data, JSON_PRETTY_PRINT | JSON_UNESCAPED_SLASHES | JSON_UNESCAPED_UNICODE)
            );

        return $response;

    }
    
    public static function errorResponse(Response $response, int $status) : Response{

        $response = $response
            ->withStatus($status);
            

        return $response;

    }
    

}