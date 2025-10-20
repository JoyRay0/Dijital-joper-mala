<?php

require_once __DIR__. '/Header.php';

$header = new HeadersManager();

$header->setAllHeaders();


$method = $_SERVER['REQUEST_METHOD'];

$Messages = new JsonMessages();

if($method !== 'GET'){

    $Messages->dieMessage("Failed", "Invalid method");

}

$apis = [

    //'jopa_info' => 'https://jopamala.rksoftwares.xyz/arrayDatabase?res=get_info',
    'mantra' => 'https://jopamala.rksoftwares.fun/Jopa_info?res=get_mantra',
    'jopa_info' => 'https://jopamala.rksoftwares.fun/Jopa_info?res=get_info',
    'inAppnotification' => 'https://jopamala.rksoftwares.fun/InAppNotification',

];

echo json_encode($apis);

?>