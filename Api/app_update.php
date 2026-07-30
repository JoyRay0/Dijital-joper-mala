<?php

require_once __DIR__. '/Header.php';
require __DIR__. '/JsonMessages.php';

$header = new HeadersManager();

$header->setAllHeaders();

$method = $_SERVER["REQUEST_METHOD"];

$messages = new JsonMessages();

if($method !== "GET"){

    $messages->errorMessage(
        "Error",
        "Invalid method"
    );

}


echo json_encode([

    "status" => "Success",
    "version" => "2.5"

], JSON_PRETTY_PRINT);