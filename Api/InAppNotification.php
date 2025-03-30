<?php

header('Content-Type: application/json; charset=utf-8');

$title = ""; // your notification title
$description = ""; // your notification message
$count = 0;

$tm = $title && $description;
if($tm > $count){

    echo json_encode([

        'msize' => 'long',
        'title' => $title,
        'description' => $description

    ]);

}else{

    echo json_encode([

        'msize' => 'sort',
        'title' => 'no title',
        'description' => 'no message'

    ]);

}

?>