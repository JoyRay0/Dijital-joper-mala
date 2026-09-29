<?php

namespace Src\Helper;

use Phpfastcache\Helper\Psr16Adapter;

class CacheHelper{

    private static Psr16Adapter $cache;

    private static function init(){

        if(!self::$cache){

            self::$cache = new Psr16Adapter('Files');

        }

    }

    public static function setStringCache(string $key, string $value, int $endTime){

        self::init();
        self::$cache->set($key, $value, $endTime);

    }

    public static function setArrayCache(string $key, array $value, int $endTime){

        self::init();
        self::$cache->set($key, $value, $endTime);

    }

    public static function getStringCache(string $key) : string{

        self::init();

        $value = self::$cache->get($key, "");

        return $value;

    }

    public static function getArrayCache(string $key) : array{

        self::init();

        $value = self::$cache->get($key, []);

        return $value;

    }

    public static function deleteStringCache(string $key){

        self::$cache->delete($key);

    }

    public static function deleteArrayCache(string $key){

        self::$cache->delete($key);

    }

}