<?php

require __DIR__ . "/../vendor/autoload.php";

use App\Controllers\Admin;
use App\Middleware\HeaderMiddleware as AllHeader;
use App\Controllers\AppUpdate;
use App\Controllers\Information;
use App\Controllers\Mantra;
use App\Controllers\Notification;
use App\Controllers\Pager;
use App\Controllers\Tracker;
use App\Middlewares\AdminCheckerMiddleware;
use App\Middlewares\DeviceIdMiddleware;
use App\Middlewares\RateLimitMiddleware;
use Dotenv\Dotenv;
use Slim\Factory\AppFactory;


$env = Dotenv::createImmutable( __DIR__ . "/..");
$env->load();

$app = AppFactory::create();

date_default_timezone_set("Asia/Dhaka");

$app->add(new AllHeader());

if(isset($_ENV['DEBUG']) && $_ENV['DEBUG'] === 'true'){

    $app->addErrorMiddleware(true, true, true);

}

$app->addRoutingMiddleware();
$app->addBodyParsingMiddleware();

/* User */

$app->group('/api', function($group){

    /*
    - GET Request
    */
    $group->get('/app_update', AppUpdate::class . ':App_update');
    $group->get('/information', Information::class . ':information');
    $group->get('/pager', Pager::class . ':pager');
    $group->get('/mantra', Mantra::class . ':mantra');

    //-----------------------

    /*
    - POST Request
    */

    $group->post('/notification', Notification::class . ':notification');
    $group->post('/tracker', Tracker::class . ':tracker');

})
->add(new DeviceIdMiddleware())
->add(new RateLimitMiddleware());

/* Admin  */

$app->group('/admin', function ($group){

    $group->post('/set_app_update', Admin::class . ':setAppUpdate');
    $group->post('/set_information', Admin::class . ':setInformation');
    $group->post('/set_mantra', Admin::class . ':setMantra');
    $group->post('/set_notification', Admin::class . ':setNotification');
    $group->post('/set_pager', Admin::class . ':setPager');
    $group->post('/set_tracker', Admin::class . ':setTracker');

    $group->post('/get_app_update', Admin::class . ':getAppUpdate');
    $group->post('/get_information', Admin::class . ':getInformation');
    $group->post('/get_mantra', Admin::class . ':getMantra');
    $group->post('/get_notification', Admin::class . ':getNotification');
    $group->post('/get_pager', Admin::class . ':getPager');
    $group->post('/get_tracker', Admin::class . ':getTracker');

})->add( new AdminCheckerMiddleware());

$app->run();