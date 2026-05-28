package com.mala.digital_joper_mala.Model

import com.mala.digital_joper_mala.Helper.CacheHelper_

data class SettingData(

    val key : String,
    val value : String

)

class SettingModel(
    private val cache : CacheHelper_
) {

    fun setCache(key: String, value: String){

        cache.setCache(key, value)

    }

    fun getModeCache(key: String, defaultValue : String) : String{

        val cache = cache.getCache(key, defaultValue)

        return cache

    }

    fun deleteCache(key: String){

        cache.deleteCache(key)

    }


}