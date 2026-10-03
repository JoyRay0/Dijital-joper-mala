<?php

namespace App\Middlewares;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;
use Src\Helper\CacheHelper as Cache;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\LogHelper as Log;
use Src\Helper\ResponseHelper as ResHelper;

class RateLimitMiddleware {

    private Response $response;

    public function __invoke(Request $request, Handler $handler): Response{

        $deviceID = $request->getHeaderLine('DeviceId');

        $count = (int) Cache::getStringCache($deviceID);

        if($count >= 60){

            return ResHelper::errorResponse(
                $this->response,
                Https::TooManyRequests->value
            );

        }

        $count++;

        Cache::setStringCache($deviceID, $count, 60);

        return $handler->handle($request);

    }

}