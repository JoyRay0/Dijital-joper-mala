<?php

header('Content-Type: application/json; charset=utf-8');
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
header("Cache-Control: public, max-age=3600");


$method = $_SERVER['REQUEST_METHOD'];

if($method !== 'GET'){

    die(json_encode([

        'status' => 'Failed',
        'message' => 'Invalid method'

    ]));

}

$apis = [

    //'jopa_info' => 'https://jopamala.rksoftwares.xyz/arrayDatabase?res=get_info',
    'mantra' => 'https://jopamala.rksoftwares.fun/Jopa_info?res=get_mantra',
    'jopa_info_db' => 'https://jopamala.rksoftwares.fun/Jopa_info?res=get_info',
    'inAppnotification' => 'https://jopamala.rksoftwares.fun/InAppNotification',

    //'jopa_info' => 'https://jopamala.rksoftwares.fun/arrayDatabase?res=get_info',
    //'jopa_info_db' => 'https://jopamala.rksoftwares.fun/Jopa_info?res=get_info',
    //'inAppnotification' => 'https://jopamala.rksoftwares.fun/InAppNotification',

];

echo json_encode($apis);

?>