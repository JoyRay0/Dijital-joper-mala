<?php
require 'db.php';
require 'middleware.php';
require 'ID_middleware.php';

header('Content-Type: application/json; charset=utf-8');

header("Access-Control-Allow-Origin: https://rksoftwares.xyz");

// Strict-Transport-Security হেডার
header('Strict-Transport-Security:max-age=31536000; includeSubDomains');

// X-Content-Type-Options হেডার
header('X-Content-Type-Options: nosniff');

// XSS সুরক্ষা হেডার
header('X-XSS-Protection: 1; mode=block');

// X-Frame-Options হেডার
header('X-Frame-Options: DENY');

// Referrer-Policy হেডার
header('Referrer-Policy: no-referrer');


//All database table with one api in Routing Logic

rate_limt();

check_deviceIds();

jopa_mala();


function jopa_mala(){

    $method = $_SERVER['REQUEST_METHOD'];
    $res = $_GET['res'] ?? '';
    global $database_connect;

    if($method != 'GET'){

        echo json_encode([

            "status" => "Falied",
            "message" => "Method not supported"

        ]);
        exit;

    }

    switch($res){

        case 'get_info':

            $sql = "SELECT * FROM jop_mala_info1";
            break;
        
        
        default:
        
            echo json_encode([

                "status" => "Failed",
                "message" => "Invalid resource type"

            ]);
            exit;

    }


    $sql_query = mysqli_query($database_connect, $sql);

    if(!$sql_query){

        echo json_encode([

            "status" => "Falied",
            "message" => "Database query failed"
        ]);
        exit;

    }

    $data = [];

    while($item = mysqli_fetch_assoc($sql_query)){

        $data[] = $item;

    }

    echo json_encode([

        "status" => "Success",
        "data" => $data

    ]);

    }


?>