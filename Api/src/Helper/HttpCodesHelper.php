<?php

namespace Src\Helper;

enum HttpCodesHelper : int{

    case Success = 200;
    case Created = 201;


    case BadRequest = 400;
    case Unauthorized = 401;
    case Forbidden = 403;
    case NotFound = 404;

    
    case ServerError = 500;

}
