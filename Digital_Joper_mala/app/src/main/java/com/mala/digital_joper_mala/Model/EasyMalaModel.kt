package com.mala.digital_joper_mala.Model

import android.content.Context
import com.mala.digital_joper_mala.Database.AllMantraDatabase
import com.mala.digital_joper_mala.Database.UserMantraDatabase
import com.mala.digital_joper_mala.Helper.CacheHelper

data class EasyMalaItem(
    val id : Int = 0,
    val title : String = "",
    val mantra : String = ""
)

class EasyMalaModel(
    private val context: Context
) {

    private val cache = CacheHelper(context, "easy_mala")
    private val allMantraDB = AllMantraDatabase(context)
    private val userMantraDB = UserMantraDatabase(context)

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

    fun getFavoriteMantra() : List<EasyMalaItem>{

        val list : MutableList<EasyMalaItem> = mutableListOf()

        val data = allMantraDB.getFavoriteMantra()

        data.forEach { result ->

            list.add(EasyMalaItem(
                title = result.title,
                mantra = result.mantra
            ))

        }

        return list

    }

    fun getUserMantra() : List<EasyMalaItem>{

        val list : MutableList<EasyMalaItem> = mutableListOf()

        val data = userMantraDB.getAllMantra()

        data.forEach { result ->

            list.add(EasyMalaItem(
                title = result.title,
                mantra = result.mantra
            ))

        }

        return list

    }


}