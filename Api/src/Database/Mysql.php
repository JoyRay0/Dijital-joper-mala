<?php

namespace Src\Database;

use PDO;
use PDOException;
use Src\Helper\HttpCodesHelper as http;
use Src\Helper\LogHelper as log;

class Mysql{

    private ?PDO $connection = null;

    private string $informationTable = "jop_mala_info1";
    private string $mantraTable = "mantras";
    private string $activityTable = "activity";
    private string $trackerTable = "tracker";

    public function __construct()
    {

        if($this->connection == null){

            $databaseHost = $_ENV['DB_HOST'];
            $databaseName = $_ENV['DB_NAME'];
            $databaseUser = $_ENV['DB_USER'];
            $databasePassword = $_ENV['DB_PASSWORD'];

            $dsn = "mysql:host=$databaseHost; dbname=$databaseName; charset=utf8";

            try{

                $this->connection = new PDO($dsn, $databaseUser, $databasePassword,
                [
                    PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                    PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                    PDO::ATTR_PERSISTENT => true
                ]);


                /*
                * Create Activity table
                */

                $this->connection->exec(
                    "CREATE TABLE IF NOT EXISTS $this->activityTable (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    activity_name TEXT UNIQUE NOT NULL,
                    duration INTEGER DEFAULT 0"
                );

                /*
                * Create Tracker table
                */

                $this->connection->exec(
                    "CREATE TABLE IF NOT EXISTS $this->trackerTable (
                    id INTEGER PRIMARY KEY AUTOINCREMENT,
                    device_id TEXT UNIQUE NOT NULL,
                    android_version TEXT NOT NULL,
                    sdk_version TEXT NOT NULL,
                    country_code TEXT NOT NULL,
                    last_open TEXT NOT NULL
                    "
                );

                $this->connection->exec(
                    "CREATE INDEX idx_device_id ON $this->trackerTable(device_id)"
                );

            }catch(PDOException $e){

                log::writeErrorLog($e->getMessage());

            }

        }
        
    }

    public function getAllInformation() : array{

        $stmt = $this->connection->query(
            "SELECT * FROM $this->informationTable"
        );

        return $stmt->fetchAll();
    }

    public function getAllMantra() : array{

        $stmt = $this->connection->query(
            "SELECT * FROM $this->mantraTable"
        );

        return $stmt->fetchAll();

    }

    public function insertOneActivity(
        string $activityName,
        int $duration
    ){

        $activityColumnName = "activity_name";
        $activityColumnDuration = "duration";

        /*
        * First check activity already save or not
        */

        $oldActivityData = $this->connection->prepare(
            "SELECT $activityColumnName, $activityColumnDuration FROM $this->activityTable WHERE $activityColumnName = ?"
        );

        $oldActivityData->execute([$activityName]);

        $oldDataResult = $oldActivityData->fetch();

        if($oldDataResult !== false){

            $updateStmt = $this->connection->prepare(
                "UPDATE $this->activityTable SET $activityColumnDuration = ? WHERE $activityColumnName = ?"
            );

            $totalDuration = ($oldDataResult[$activityColumnDuration] + $duration);

            $updateStmt->execute([$totalDuration, $activityName]);

            return;

        }

        /*
        * If not found in database then insert it.
        */

        $stmt = $this->connection->prepare(
            "INSERT INTO $this->activityTable ($activityColumnName, $activityColumnDuration) VALUES (?, ?)"
        );

        $stmt->execute([$activityName, $duration]);

    }

    public function getActivityData() : array{

        $stmt = $this->connection->query(
            "SELECT * FROM $this->activityTable"
        );

        return $stmt->fetchAll();

    }

    public function insertOneDeviceItem(
        string $device_id,
        string $android_version,
        string $sdk_version,
        string $country_code,
        string $last_open
    ){

        $trackDeviceId = "device_id";
        $trackAndroidVersion = "android_version";
        $trackSqkVersion = "sdk_version";
        $trackCountryCode = "country_code";
        $trackLastOpen = "last_open";

        

    }

}