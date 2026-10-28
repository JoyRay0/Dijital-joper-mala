<?php

namespace Src\Helper;

class LogHelper{

    private static string $log_folder_path = "Api/src/Logs/";

    public static function writeNormalLog(string $tag, string $message): void{

        $normal_folder_path = self::$log_folder_path . "Normal/";

        $currentDateTime = date("Y-m-d H:i:s");

        $logData = "{$currentDateTime} >> [{$tag}] >> {$message}" . PHP_EOL;

        file_put_contents(
            $normal_folder_path . "/app_normal.log", 
            $logData,
            FILE_APPEND | LOCK_EX);

    }

    public static function writeErrorLog(string $message): void{

        $error_folder_path = self::$log_folder_path . "Error/";

        $currentDateTime = date("Y-m-d H:i:s");

        $logData = "{$currentDateTime} >> [ERROR] >> {$message}" . PHP_EOL;

        file_put_contents(
            $error_folder_path . "/app_error.log", 
            $logData,
            FILE_APPEND | LOCK_EX);

    }



}