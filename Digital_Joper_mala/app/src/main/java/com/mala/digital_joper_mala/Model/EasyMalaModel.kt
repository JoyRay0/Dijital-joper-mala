package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Helper.CacheHelper_


class EasyMalaModel(
    private val context: Context
) {

    private val cache = CacheHelper_(context, "easy_mala")

    fun setLastCountCache(value : String){

        if (value.isEmpty()) return

        cache.setCache("easy_mala_count", value)

    }

    fun getLastCountCache() : String{

        return cache.getCache("easy_mala_count", "")

    }

    fun setCountLimit(value: String){

        if (value.isEmpty()) return

        cache.setCache("easy_mala_count_limit", value)

    }

    fun getCountLimit() : String{

        return cache.getCache("easy_mala_count_limit", "")

    }

}