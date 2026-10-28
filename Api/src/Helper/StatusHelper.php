<?php

namespace Src\Helper;

enum StatusHelper : string{

    case Success = "Success";
    case Failed = "Failed";
    case Error = "Error";
    case Bad_Request = "Bad Request";
    case Not_Found = "Not Found";

}