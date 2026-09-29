<?php

require __DIR__ . "/Api/vendor/autoload.php";

use App\Middleware\HeaderMiddleware as AllHeader;
use App\Controllers\AppUpdate;
use App\Controllers\Information;
use App\Controllers\Mantra;
use App\Controllers\Notification;
use App\Controllers\Pager;
use App\Controllers\Tracker;
use App\Middlewares\DeviceIdMiddleware;
use App\Middlewares\RateLimitMiddleware;
use Dotenv\Dotenv;
use Slim\Factory\AppFactory;



$env = Dotenv::createImmutable( __DIR__ . "/Api");
$env->load();

$app = AppFactory::create();

date_default_timezone_set("Asia/Dhaka");

if(isset($_ENV['DEBUG']) && $_ENV['DEBUG'] === 'false'){

    $app->add(new DeviceIdMiddleware());
    $app->add(new RateLimitMiddleware());

}else{

    $app->addErrorMiddleware(true, true, true);

}

$app->addRoutingMiddleware();
$app->addBodyParsingMiddleware();
$app->add(new AllHeader());


/*

- GET Request

*/
$app->get('/app_update', AppUpdate::class . ':App_update');
$app->get('/information', Information::class . ':information');
$app->get('/pager', Pager::class . ':pager');
$app->get('/mantra', Mantra::class . ':mantra');

//-----------------------

/*

- POST Request

*/

$app->post('/notification', Notification::class . ':notification');
$app->post('/tracker', Tracker::class . ':tracker');

$app->run();
    