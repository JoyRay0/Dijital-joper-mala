<?php

namespace App\Middlewares;

use Psr\Http\Message\ResponseFactoryInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;
use Src\Helper\CacheHelper as Cache;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\LogHelper as Log;
use Src\Helper\ResponseHelper as ResHelper;

class RateLimitMiddleware {

    private ResponseFactoryInterface $response;

    public function __construct(private int $limit, ResponseFactoryInterface $response)
    {

        $this->response = $response;
        
    }

    public function __invoke(Request $request, Handler $handler): Response{

        $deviceID = $request->getHeaderLine('device_id');

        $count = (int) Cache::getStringCache($deviceID);

        $response = $this->response->createResponse();

        if($count >= $this->limit){

            return ResHelper::errorResponse(
                $response,
                Https::TooManyRequests->value
            );

        }

        $count++;

        Cache::setStringCache($deviceID, $count, 60);

        return $handler->handle($request);

    }

}