<?php

$database_host = "localhost";
$database_user = "jekkhjpw_softwares";
$database_password = "qKAz40Y2@2A]dg";
$database_name = "jekkhjpw_rk_softwares";

//10
$dsn = "mysql:host=$database_host; dbname=$database_name; charset=utf8";

try{

    $pdo = new PDO($dsn, $database_user, $database_password);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    echo "Database connected";

}catch(PDOException $e){

    echo "not connected = ".$e->getMessage();

}
?>