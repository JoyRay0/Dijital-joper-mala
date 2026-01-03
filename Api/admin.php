<?php

require_once __DIR__ ."/db.php";
require_once __DIR__ ."/Header.php";
require_once __DIR__ ."/Cache.php";
require_once __DIR__ ."/JsonMessages.php";

$method = $_SERVER["REQUEST_METHOD"];
$res = $_GET["res"] ?? "";
$deleteRes = $_GET["delete_res"] ??"";

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


verifyAdmin($data);

switch($res){

    case "notify":

        setNotification($data);

        break;

    case "delete":

        delete($deleteRes, $data);

        break;

    default:
        insertData($res, $data);

}



function verifyAdmin($data){

    global $pdo, $jsonMessage;

    $email = trim(filter_var($data["email"], FILTER_SANITIZE_EMAIL) ?? "");
    $password = $data["password"] ?? "";

    if(empty($email) || empty($password)){

        $jsonMessage->errorMessage("failed", "email or password filed can not be empty");

    }

    //db connection

    try{

        $checkUser = $pdo->prepare("SELECT email, password FROM admin WHERE email = ? LIMIT 1");
        $checkUser->execute([$email]);
        $admin = $checkUser->fetch(PDO::FETCH_ASSOC);

        if(!$admin){

            $jsonMessage->errorMessage("failed", "user not found");

        }

        //verifying user password
        if(!password_verify($password, $admin["password"])){

            $jsonMessage->errorMessage("failed", "invalid password");

        }

       // $jsonMessage->successMessage("success", "database", "verification successful");


    }catch(PDOException $e){

       $jsonMessage->errorMessage("error", "Server error");

    }
    
}//function end

function insertData($res, $data){

    global $pdo, $jsonMessage;

    $title = trim(htmlspecialchars($data["title"], ENT_QUOTES, "UTF-8") ?? "");
    $description = trim(htmlspecialchars($data["description"], ENT_QUOTES, "UTF-8") ?? "");

    if(empty($title) || empty($description)){

        $jsonMessage->errorMessage("failed", "title or description can not be empty");

    }

    switch($res){

        case "jopa_info":

            $stmt = $pdo->prepare("INSERT INTO jop_mala_info1 (question, answer) VALUES (?, ?)");

            $params = [$title, $description];

            break;

        case "mantra":

            $stmt = $pdo->prepare("INSERT INTO mantras (title, mantra) VALUES (?, ?)");

            $params = [$title, $description];

            break;

        default:
            $jsonMessage->errorMessage("failed", "wrong res method");

    }

    try{

        $stmt->execute($params);
        
        $result = $stmt->rowCount();

        if($result > 0){

            $jsonMessage->successMessage("success", "database", "Insert successfuly");

        }else{

            $jsonMessage->errorMessage("failed", "Data not inserted");

        }

    }catch(PDOException $e){

        $jsonMessage->errorMessage("error", "data not inserted server error");

    }

}//function end

function setNotification($data){

    global $jsonMessage;

    $title = trim(htmlspecialchars($data["title"], ENT_QUOTES, "UTF-8") ?? "");
    $description = trim(htmlspecialchars($data["description"], ENT_QUOTES,"UTF-8") ?? "");
    $imageLink = trim(htmlspecialchars($data["image"], ENT_QUOTES, "UTF-8") ?? "");
    $webLink = trim(htmlspecialchars($data["web"], ENT_QUOTES, "UTF-8") ?? "");
    $endDate = trim(htmlspecialchars($data["date"], ENT_QUOTES, "UTF-8") ?? "");

    if(empty($title) || empty($description) || empty($endDate)){

        $jsonMessage->errorMessage("failed", "main filed are missing");

    }

    //formating date

    $dt = DateTime::createFromFormat("d-m-Y", $endDate);

    if(!$dt) $jsonMessage->errorMessage("failed", "invalid date format use this (-)");

    $date = $dt->format("Y-m-d");

    //formating date

    $notification = [

        "title" => $title,
        "description" => $description,
        "imageLink" => $imageLink,
        "webLink" => $webLink,
        "endDate" => $date

    ];

    if(file_exists("notification.json")) unlink("notification.json");

    $file =  file_put_contents("notification.json", json_encode($notification, JSON_PRETTY_PRINT | JSON_UNESCAPED_UNICODE));


    if($file){

        $jsonMessage->successMessage("successful","api", "json file created");

    }else{

        $jsonMessage->errorMessage("failed","json file not created");

    }


}//function end

function delete($deleteRes, $data){

    global $jsonMessage, $pdo, $cache;

    $title = trim(htmlspecialchars($data["title"], ENT_QUOTES, "UTF-8") ?? "");

    switch($deleteRes){

        case "delete_jopa_info":

            deleteHelper($title, "jop_mala_info1", "question");

            $stmt = $pdo->prepare("DELETE FROM jop_mala_info1 WHERE question = ?");
            $params = [$title];

            break;

        case "delete_mantra":

            deleteHelper($title, "mantras", "title");

            $stmt = $pdo->prepare("DELETE FROM mantras WHERE title = ?");
            $params = [$title];

            break;

        case "delete_notification":

            if(file_exists("notification.json")){

                unlink("notification.json"); 
                
                $jsonMessage->successMessage("success", "api", "notification delete successfully");

            }else{

                $jsonMessage->errorMessage("failed", "notification not deleted");

            }

            break;

        default:
            
            $jsonMessage->errorMessage("error", "wrong delete res");

    }

    try{

        $stmt->execute($params);
        
        $result = $stmt->rowCount();

        if($result > 0){

            $jsonMessage->successMessage("success", "database", "delete successfuly");

            $cache->clearCache();

        }else{

            $jsonMessage->errorMessage("failed", "Data not deleted");

        }

    }catch(PDOException $e){

        $jsonMessage->errorMessage("error", "data not deleted server error");

    }

}//function

function deleteHelper($title, $table_name, $title_name){

    global $jsonMessage, $pdo;

    if(empty($title)) $jsonMessage->errorMessage("failed", "title can not be empty");

    $stmt = $pdo->prepare("SELECT {$title_name} FROM {$table_name} WHERE {$title_name} = ?");

    try{

        $stmt->execute([$title]);

        $data = $stmt->fetch(PDO::FETCH_ASSOC);

        if(!$data){

            $jsonMessage->errorMessage("failed", "title not found");

        }

    }catch(PDOException $e){

        $jsonMessage->errorMessage("error", "data not found in server");

    }


}//function
