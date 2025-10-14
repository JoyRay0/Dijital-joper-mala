<?php
require 'db.php';
//require 'middleware.php';
//require 'ID_middleware.php';
require_once __DIR__. '/Cache.php';

header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");

header('Content-Type: application/json; charset=utf-8');

header("Access-Control-Allow-Origin:*");

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

header("Cache-Control: public, max-age=3600");


//All database table with one api in Routing Logic

//rate_limt();

//check_deviceIds();

jopa_mala();


function jopa_mala(){

    $method = $_SERVER['REQUEST_METHOD'];
    $res = $_GET['res'] ?? '';
    global $pdo;

    $cache = new Cache();

    if($method !== 'GET'){

        die (json_encode([

            "status" => "Falied",
            "message" => "Method not supported"

        ]));
       

    }

    switch($res){

        case 'get_info':

           
            $data =  $cache->getCache($res.'_cache');

            if($data){

                echo json_encode([

                    "status" => "sucess",
                    "form" => "Cache",
                    "data" => $data
                ]);
                exit;
            }

            $stmt = $pdo->prepare("SELECT * FROM jop_mala_info1");

            break;
        
        case 'get_mantra':

             $data =  $cache->getCache($res.'_cache');

            if($data){

                echo json_encode([

                    "status" => "sucess",
                    "form" => "Cache",
                    "data" => $data
                ]);
                exit;
            }

            $stmt = $pdo->prepare("SELECT * FROM mantras");

            break;    

        default:
        
            echo json_encode([

                "status" => "Failed",
                "message" => "Invalid resource type"

            ]);
            exit;

    }

    $stmt->execute();

    $sql_query = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if(!$sql_query){

        echo json_encode([

            "status" => "Falied",
            "message" => "Database query failed"
        ]);
        exit;

    }

    $cache->setCache($res."_cache", $sql_query, 5);

    echo json_encode([

        "status" => "Success",
        "from" => "database",
        "data" => $sql_query

    ]);

}

?>