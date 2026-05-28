package com.mala.digital_joper_mala.Helper

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class CacheHelper_(
    private val context: Context,
    private val preference : String
) {

    private var sp : SharedPreferences? = null

    fun setCache(key : String, value : String){

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE)
        sp?.edit { putString(key, value) }

    }

    fun getCache(key: String, defaultValue : String) : String{

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE)
        val values = sp?.getString(key, defaultValue)

        return values!!

    }

    fun deleteCache(key: String){

        sp = context.getSharedPreferences(preference, Context.MODE_PRIVATE)
        sp?.edit { remove(key) }

    }

}//class==============