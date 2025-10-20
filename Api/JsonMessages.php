<?php

class JsonMessages{

    function dieMessage($status, $messages ){

        die(json_encode([

            'status' => $status,
            'messages' => $messages,

        ]));

    }

    function errorMessage($status, $messages){

        echo json_encode([

            'status' => $status,
            'messages' => $messages,

        ]);
        exit();

    }

    function successMessage($status, $from, $data){

        echo json_encode([

            'status' => $status,
            'from' => $from,
            'data' => $data,

        ]);
        exit;

    }

}