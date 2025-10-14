<?php

use Phpfastcache\Helper\Psr16Adapter;

require_once __DIR__. '/vendor/autoload.php';

class Cache {

    private $cache;

    public function __construct($type = 'Files'){


        $this->cache = new Psr16Adapter($type);

    }

    //cache set
    public function setCache($key, $value, $maxAge ){


        $expiry_time = $maxAge * 60;

        return  $this->cache->set($key, $value, $$expiry_time);
    }

    //cache get
    public function getCache($key){


        $value = $this->cache->get($key);

        if($value === null){

           $this->cache->delete($key);
        }
        

        return $value;

    }


    //cache delete
    public function deleteCache($key){

        return $this->cache->delete($key);

    }

    //cache clear
    public function clearCache(){


        $this->cache->clear();

    }

    //cache exits
    public function exists($key){


       return $this->cache->has($key);

    }


}