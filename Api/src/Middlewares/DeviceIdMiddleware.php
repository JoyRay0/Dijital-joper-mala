<?php

namespace App\Middlewares;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;
use Src\Helper\LogHelper as log;
use Src\Helper\HttpCodesHelper as https;
use Src\Helper\ResponseHelper as ResHelper;

class DeviceIdMiddleware {

    private Response $response;

    public function __invoke(Request $request, Handler $handler): Response{

        $deviceID = $request->getHeaderLine('DeviceId');

        if(empty($deviceID)){

            log::writeNormalLog("Device_ID_header", "Device id not found");
            
            return ResHelper::errorResponse(
                $this->response,
                https::Unauthorized->value
            );

        }

        return $handler->handle($request);

    }

}