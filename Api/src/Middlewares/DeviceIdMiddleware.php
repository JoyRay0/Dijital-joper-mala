<?php

namespace App\Middlewares;

use Psr\Http\Message\ResponseFactoryInterface;
use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;
use Src\Helper\LogHelper as Log;
use Src\Helper\HttpCodesHelper as Https;
use Src\Helper\ResponseHelper as ResHelper;

class DeviceIdMiddleware {

    private ResponseFactoryInterface $response;

    public function __construct(ResponseFactoryInterface $response)
    {
        $this->response = $response;
    }

    public function __invoke(Request $request, Handler $handler): Response{

        $deviceID = $request->getHeaderLine('device_id');
        $ip = $_SERVER['REMOTE_ADDR'];

        if(empty($deviceID)){

            Log::writeNormalLog("Device_ID_header", "Device id not found in header from ip = [$ip]");
            
            $response = $this->response->createResponse();

            return ResHelper::errorResponse(
                $response,
                Https::Unauthorized->value
            );

        }

        return $handler->handle($request);

    }

}