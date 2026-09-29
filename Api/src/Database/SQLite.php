<?php

namespace Src\Database;

use PDO;
use PDOException;
use Src\Helper\HttpCodesHelper as http;
use Src\Helper\LogHelper as log;

enum SQLiteHelper : string{

    case TABLE_NAME = "notification";
    case TITLE = "title";
    case DESCRIPTION = "description";

}

class SQLite{

    private ?PDO $connection = null;
    private string $tableName = "notification";
    private string $title = "title";
    private string $description = "description";
    private string $startAt = "start_at";
    private string $endAt = "end_at";

    public function __construct()
    {

        if($this->connection == null){

            try{

                $this->connection = new PDO("sqlite:database/database.db", null, null,
                    [
                        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION,
                        PDO::ATTR_DEFAULT_FETCH_MODE => PDO::FETCH_ASSOC,
                    ]);

            }catch(PDOException $e){

                log::writeErrorLog($e->getMessage());

                http_response_code(http::ServerError->value);

            }
    
        }

        $this->connection->exec("
        CREATE TABLE IF NOT EXISTS $this->tableName (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        title TEXT NOT NULL,
        description TEXT NOT NULL,
        start_at TEXT NOT NULL,
        end_at TEXT NOT NULL)");
        
    }

    public function insertNotification(
        string $title,
        string $description,
        string $startAt,
        string $endAt
    ){

        if(empty($title) || empty($description) || empty($startAt) || empty($endAt)){

            return;

        }

        $stmt = $this->connection->prepare(
            "INSERT INTO $this->tableName
            ($this->title, $this->description, $this->startAt, $this->endAt) VALUES
            (:$this->title, :$this->description, :$this->startAt, :$this->endAt)
            ");

        $stmt->execute([
            ":$this->title" => $title,
            ":$this->description" => $description,
            ":$this->startAt" => $startAt,
            ":$this->endAt" => $endAt
        ]);


    }

    public function getNotification() : array{

        $stmt = $this->connection->query(
            "SELECT * FROM $this->tableName"
        );

        $stmt->execute();

        return $stmt->fetchAll();

    }

    public function getSingleNotification(string $date) : array{

        $stmt = $this->connection->prepare(
            "SELECT * FROM $this->tableName WHERE $this->startAt = ?"
        );

        $stmt->execute([$date]);

        return $stmt->fetchAll();
    }

    public function deleteNotification(int $id) : bool {
    
        $stmt = $this->connection->prepare(
            "DELETE FROM $this->tableName WHERE id = ?"
        );

        $stmt->execute([$id]);

        return $stmt->rowCount() > 0;

    }

}