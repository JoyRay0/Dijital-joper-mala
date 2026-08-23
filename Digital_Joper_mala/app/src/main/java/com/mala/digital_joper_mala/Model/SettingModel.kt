package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Helper.CacheHelper

data class SettingData(

    val key : String,
    val value : String

)

class SettingModel(
    private val context : Context
) {

    private val cache = CacheHelper(context, "settings")

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