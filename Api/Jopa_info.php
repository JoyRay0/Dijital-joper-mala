<?php
require 'db.php';
require_once __DIR__. '/Cache.php';
require_once __DIR__. '/Header.php';
require __DIR__. '/JsonMessages.php';
require_once 'EnvHelper.php';


$header = new HeadersManager();

$header->setAllHeaders();

$Messages = new JsonMessages();
$cache = new Cache();

loadEnv();
jopa_mala();

function jopa_mala(){

    $method = $_SERVER['REQUEST_METHOD'];
    $res = $_GET['res'] ?? '';

    global $pdo, $cache, $Messages;

    if($method !== 'GET'){
     
        $Messages->dieMessage("Failed", "Method not supported");
       
    }

    switch($res){

        case 'get_info':

            cacheData($res);

            $stmt = $pdo->prepare("SELECT * FROM jop_mala_info1");

            break;
        
        case 'get_mantra':

            cacheData($res);

            $stmt = $pdo->prepare("SELECT * FROM mantras");

            break;    

        default:

            $Messages->errorMessage("Failed", "Invalid resource type");

    }

    try{

        $stmt->execute();
        $sql_query = $stmt->fetchAll(PDO::FETCH_ASSOC);

    }catch(PDOException $e){

        if(getenv('DEBUG') === 'true'){

            echo $e->getMessage();

        }else{

            $Messages->errorMessage("Failed", "Database query failed");

        }

    }

    if(getenv('DEBUG') === 'true'){

        echo "Cache Disabled";

    }else{

        $cache->setCache($res."_cache", $sql_query, 5);

    }

    $Messages->successMessage("success", "database", $sql_query);

}

function cacheData($res){

    global $Messages, $cache;
    
    if(getenv('DEBUG') === 'true'){

        echo "Cache Disable";

    }else{

        $data =  $cache->getCache($res.'_cache');

    }

    if($data){

        $Messages->successMessage("success", "Cache", $data);

    }
    
}

?>