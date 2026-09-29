<?php

namespace App\Middlewares;

use Psr\Http\Message\ServerRequestInterface as Request;
use Psr\Http\Message\ResponseInterface as Response;
use Psr\Http\Server\RequestHandlerInterface as Handler;

class RateLimitMiddleware {

    public function __invoke(Request $request, Handler $handler): Response{

        return $handler->handle($request);

    }

}