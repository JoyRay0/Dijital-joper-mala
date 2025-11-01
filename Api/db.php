<?php

require_once 'EnvHelper.php';

loadEnv();

$database_host = getenv('DB_HOST');
$database_user = getenv('DB_USER');
$database_password = getenv('DB_PASSWORD');
$database_name = getenv('DB_NAME');


$dsn = "mysql:host=$database_host; dbname=$database_name; charset=utf8";

try{

    $pdo = new PDO($dsn, $database_user, $database_password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    echo "Database connected";

}catch(PDOException $e){

    if(getenv('DEBUG') === 'true'){

        echo $e->getMessage();
       
    }else{

        echo "Database connection failed";

    }

}
?>