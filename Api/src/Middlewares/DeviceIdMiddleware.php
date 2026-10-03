<?php

namespace App\Middlewares;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;
use Src\Helper\LogHelper as Log;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\ResponseHelper as ResHelper;

class DeviceIdMiddleware {

    private Response $response;

    public function __invoke(Request $request, Handler $handler): Response{

        $deviceID = $request->getHeaderLine('DeviceId');
        $ip = $_SERVER['REMOTE_ADDR'];

        if(empty($deviceID)){

            Log::writeNormalLog("Device_ID_header", "Device id not found in header from ip = [$ip]");
            
            return ResHelper::errorResponse(
                $this->response,
                Https::Unauthorized->value
            );

        }

        if(!preg_match(
            '/^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$/',
            $deviceID
        )){

            Log::writeNormalLog("DeviceId Format", "Wrong device id format from ip = [$ip]");

            return ResHelper::errorResponse(
                $this->response,
                Https::Unauthorized->value
            );

        }

        return $handler->handle($request);

    }

}