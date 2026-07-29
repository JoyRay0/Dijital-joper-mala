<?php

require_once __DIR__. '/Header.php';
require __DIR__. '/JsonMessages.php';
require_once 'EnvHelper.php';

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

$pager_data = [

    [
        "id" => 1,
        "image" => "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.ga6LI_QB47oanFApdRo1DAHaFj%3Fr%3D0%26pid%3DApi&f=1&ipt=1e60fe43b05706096a0d9f45cab7fc659082e3ac6bc8393536257f9e396dad7a&ipo=images"

    ],
    [

        "id" => 2,
        "image" => "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.J-S3XWV4XmHZUNp12giaJgHaFW%3Fr%3D0%26pid%3DApi&f=1&ipt=fbd72301c61c7d1d0af0066ed7dcc7faa3a31b77a2b2ad383e6ce3c675e11721&ipo=images"

    ],
    [
        "id" => 3,
        "image" => "https://external-content.duckduckgo.com/iu/?u=https%3A%2F%2Ftse2.mm.bing.net%2Fth%2Fid%2FOIP.d7aMUU6bht4MPikLfYZg5gHaE7%3Fr%3D0%26pid%3DApi&f=1&ipt=3ecc5f97ef719bf29bd8e63ee0f2d77ddd5618d8ff3783574efab9875ea29a48&ipo=images"
    ]

];

echo json_encode([

    "status" => "Success",
    "data" => $pager_data

], JSON_PRETTY_PRINT);