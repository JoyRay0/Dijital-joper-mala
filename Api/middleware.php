<?php

header('Content-Type: application/json; charset=utf-8');
//Rate Limiting middleware 
function rate_limt(){

    session_start();
    $ip = $_SERVER['REMOTE_ADDR'];  // User's IP address
    $now = time(); 

    if (!isset($_SESSION[$ip])) {
        // If not, set initial values for this IP
        $_SESSION[$ip] = [
            'last_time' => $now,
            'count' => 1
        ];
    } else {
        // If the session is set, check if more than 1 minute has passed
        if ($now - $_SESSION[$ip]['last_time'] >= 60) {
            // Reset the session count after 1 minute
            $_SESSION[$ip] = [
                'last_time' => $now,
                'count' => 1
            ];
        } else {
            // If less than 1 minute, increment the request count
            $_SESSION[$ip]['count'] = ($_SESSION[$ip]['count'] ?? 0) + 1;
        }
    }

    // Check if more than 3 requests are made
    if ($_SESSION[$ip]['count'] > 3) {
        die(json_encode([
            'status' => 'Error',
            'message' => 'Too many requests! Please wait for 1 minute.'
        ]));
    }
}



?>