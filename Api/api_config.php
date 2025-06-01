<?php

header('Content-Type: application/json; charset=utf-8');
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Cache-Control: public, max-age=3600");


$method = $_SERVER['REQUEST_METHOD'];

if($method != 'GET'){

    die(json_encode([

        'status' => 'Failed',
        'message' => 'Invalid method'

    ]));

}

$apis = [

    'jopa_info' => 'https://rksoftwares.xyz/All_app/jopa_mala/Jopa_info?res=get_info',
    'inAppnotification' => 'https://rksoftwares.xyz/All_app/jopa_mala/InAppNotification',



];

echo json_encode($apis);

?>