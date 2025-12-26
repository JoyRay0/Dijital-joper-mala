<?php

require_once __DIR__ ."/db.php";
require_once __DIR__ ."/Header.php";
require_once __DIR__ ."/Cache.php";
require_once __DIR__ ."/JsonMessages.php";

$method = $_SERVER["REQUEST_METHOD"];
$res = $_GET["res"] ?? "";

$jsonMessage = new JsonMessages();
$header = new HeadersManager();
$cache = new Cache();

$header->setAllHeaders();

//checking request method

if($method !== "POST"){

    $jsonMessage->errorMessage("failed", "wrong method");

}

//user data from json

$data = json_decode(file_get_contents("php://input"), true);


check($data, $res);
insertData($res);

function check($data, $res){

    global $cache, $pdo, $jsonMessage;

    $email = trim(filter_var($data["email"], FILTER_SANITIZE_EMAIL) ?? "");
    $password = $data["password"] ?? "";

    $title = trim(htmlspecialchars($data["title"], ENT_QUOTES, 'UTF-8') ?? "");
    $description = trim(htmlspecialchars($data["description"], ENT_QUOTES, 'UTF-8') ?? "");

    if(empty($email) || empty($password) || empty($title) || empty($description)){

        $jsonMessage->errorMessage("failed", "Some filed are empty");

    }

    //saving data in cache
    $cache->setCache($res."_cache", [

        "title" => $title,
        "description" => $description

    ], 1);

    //db connection

    try{

        $checkUser = $pdo->prepare("SELECT email, password FROM admin WHERE email = ? LIMIT 1");
        $checkUser->execute([$email]);
        $admin = $checkUser->fetch(PDO::FETCH_ASSOC);

        if(!$admin){

            $jsonMessage->errorMessage("failed", "user not found");

            $cache->clearCache();

        }

        //verifying user password
        if(!password_verify($password, $admin["password"])){

            $jsonMessage->errorMessage("failed", "invalid password");

            $cache->clearCache();

        }

        $jsonMessage->successMessage("success", "database", "verification successful");


    }catch(PDOException $e){

       $jsonMessage->errorMessage("error", "Server error");

       $cache->clearCache();

    }
    
}//function end

function insertData($res){

    global $pdo, $jsonMessage, $cache;

    switch($res){

        case "jopa_info":

            if(!$cache->exists("jopa_info_cache")){

                $jsonMessage->errorMessage("failed","Please try again");

            }

            $data = $cache->getCache("jopa_info_cache");

            $stmt = $pdo->prepare("INSERT INTO jop_mala_info1 (question, answer) VALUES (?, ?)");

            $params = [$data["title"], $data["description"]];

            break;

        case "mantra":

            if(!$cache->exists("mantra_cache")){

                $jsonMessage->errorMessage("failed","Please try again");

            }

            $data = $cache->getCache("mantra_cache");

            $stmt = $pdo->prepare("INSERT INTO mantras (title, mantra) VALUES (?, ?)");

            $params = [$data["title"], $data["description"]];

            break;

        default:
            $jsonMessage->errorMessage("failed", "wrong res method");

            $cache->clearCache();

    }

    try{

        $stmt->execute($params);
        
        $result = $stmt->rowCount();

        if($result > 0){

            $jsonMessage->successMessage("success", "database", "Insert successfuly");

            $cache->clearCache();

        }else{

            $jsonMessage->errorMessage("failed", "Data not inserted");

            $cache->clearCache();

        }

    }catch(PDOException $e){

        $jsonMessage->errorMessage("error", "data not inserted server error");

        $cache->clearCache();

    }

}//function end
