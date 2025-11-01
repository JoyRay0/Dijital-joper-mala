<?php

require_once __DIR__. '/Header.php';
require __DIR__. '/JsonMessages.php';
require_once 'EnvHelper.php';

$header = new HeadersManager();

$header->setAllHeaders();
loadEnv();

$method = $_SERVER['REQUEST_METHOD'];

$Messages = new JsonMessages();

if($method !== 'GET'){

    $Messages->dieMessage("Failed", "Invalid method");

}

$apis = [

    //'jopa_info' => 'https://jopamala.rksoftwares.xyz/arrayDatabase?res=get_info',
    'mantra' => getenv('API_URL').'/Jopa_info?res=get_mantra',
    'jopa_info' => getenv('API_URL').'/Jopa_info?res=get_info',
    'inAppnotification' => getenv('API_URL').'/InAppNotification',

];

echo json_encode($apis);

?>