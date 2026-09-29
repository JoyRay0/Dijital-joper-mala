<?php

namespace Src\Helper;

class LogHelper{

    public static function writeNormalLog(string $tag, string $message): void{

        $log_folder_path = __DIR__ . '/../Logs/Normal';

        $currentDateTime = date("Y-m-d H:i:s");

        $logData = "{$currentDateTime} >> [{$tag}] >> {$message}" . PHP_EOL;

        file_put_contents(
            $log_folder_path . "/app_normal.log", 
            $logData,
            FILE_APPEND | LOCK_EX);

    }

    public static function writeErrorLog(string $message): void{

        $log_folder_path = __DIR__ . '/../Logs/Error';

        $currentDateTime = date("Y-m-d H:i:s");

        $logData = "{$currentDateTime} >> [ERROR] >> {$message}" . PHP_EOL;

        file_put_contents(
            $log_folder_path . "/app_error.log", 
            $logData,
            FILE_APPEND | LOCK_EX);

    }



}