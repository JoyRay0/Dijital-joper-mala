<?php

require_once __DIR__. '/Header.php';
require_once __DIR__.'/JsonMessages.php';

$header = new HeadersManager();
$jsonMessage = new JsonMessages();

$method = $_SERVER["REQUEST_METHOD"];

if($method !== "GET"){

    $jsonMessage->dieMessage("error", "Invalid method");

}

$header->setAllHeaders();

$jsonFile = "notification.json";

if(!file_exists($jsonFile)){

     echo json_encode([

        'msize' => 'short',
        
    ]);
    exit;

}

$data = json_decode(file_get_contents($jsonFile), true);

$title = $data["title"];
$description = $data["description"];
$img_link = $data["imageLink"];
$web_link = $data["webLink"];


// notification end time

$date = $data["endDate"];  // end date
$time = "23:59:59";   // end time

$end_time = $date. " " .$time;

if(time() <= strtotime($end_time)){


    if(
        (!empty($title) && !empty($description)) || 
        (!empty($img_link) || !empty($web_link))
        ){

        echo json_encode([

            'msize' => 'long',
            'title' => $title,
            'description' => $description,
            'img_link' => $img_link,
            'web_link' => $web_link

        ]);
        exit;

    }else{

        echo json_encode([

            'msize' => 'short',
            'title' => 'no title',
            'description' => 'no message'

        ]);
        exit;

    }

}else{

    if(file_exists($jsonFile)) unlink($jsonFile);
    
    // time expired
    echo json_encode([

        'msize' => 'short',
        
    ]);

    exit;
}

?>