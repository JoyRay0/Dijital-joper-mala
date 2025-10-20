<?php
require 'db.php';
require_once __DIR__. '/Cache.php';
require_once __DIR__. '/Header.php';
require __DIR__. '/JsonMessages.php';

$header = new HeadersManager();

$header->setAllHeaders();


jopa_mala();

$Messages = new JsonMessages();

function jopa_mala(){

    $method = $_SERVER['REQUEST_METHOD'];
    $res = $_GET['res'] ?? '';
    global $pdo;

    $cache = new Cache();
    global $Messages;

    if($method !== 'GET'){
     
        $Messages->dieMessage("Falied", "Method not supported");
       
    }

    switch($res){

        case 'get_info':

            cacheData($res, $cache);

            $stmt = $pdo->prepare("SELECT * FROM jop_mala_info1");

            break;
        
        case 'get_mantra':

            cacheData($res, $cache);

            $stmt = $pdo->prepare("SELECT * FROM mantras");

            break;    

        default:

            $Messages->errorMessage("Failed", "Invalid resource type");

    }

    $stmt->execute();

    $sql_query = $stmt->fetchAll(PDO::FETCH_ASSOC);

    if(!$sql_query){

        $Messages->errorMessage("Falied", "Database query failed");

    }

    $cache->setCache($res."_cache", $sql_query, 5);

    $Messages->successMessage("Success", "database", $sql_query);

}

function cacheData($res, $cache){

    global $Messages;
    
    $data =  $cache->getCache($res.'_cache');

    if($data){

        $Messages->successMessage("sucess", "Cache", $data);

    }
    
}

?>