<?php

header('Content-Type: application/json; charset=utf-8');

$title = ""; // your notification title
$description = ""; // your notification message
$img_link = ""; // yuor image link
$web_link = ""; //your website link
$count = 0;

// notification end time

$date = "2025-05-05";  // end date
$time = "23:59:59";   // end time

$end_time = $date. " " .$time;


if(time() <= strtotime($end_time)){

    $tm = ($title && $description) || ($img_link || $web_link);
    if($tm > $count){

        echo json_encode([

            'msize' => 'long',
            'title' => $title,
            'description' => $description,
            'img_link' => $img_link,
            'web_link' => $web_link

        ]);

    }else{

        echo json_encode([

            'msize' => 'short',
            'title' => 'no title',
            'description' => 'no message'

        ]);

    }

}else{

    // time expired
    echo json_encode([

        'msize' => 'short',
        

    ]);

}

?>