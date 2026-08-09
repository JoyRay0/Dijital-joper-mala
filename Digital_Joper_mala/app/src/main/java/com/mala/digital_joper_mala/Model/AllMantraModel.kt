package com.mala.digital_joper_mala.Model

import android.content.Context
import androidx.compose.runtime.Composable
import com.mala.digital_joper_mala.Database.AllMantraDatabase
import com.mala.digital_joper_mala.Helper.CacheHelper_

data class Mantra(
    val status : String = "",
    val message : String = "",
    val data : List<MantraItem> = emptyList()
)

data class MantraItem(
    val id : Int = 0,
    val title : String = "",
    val mantra : String ="",
)

class AllMantraModel(
   private val context: Context
){
    private val cache = CacheHelper_(context, "All_Mantra")

    private val db = AllMantraDatabase(context)

    private val cacheKey = "all_mantra"

    fun setAllMantraCache(value : Boolean){

        cache.setCache(cacheKey, value.toString())

    }

    fun getAllMantraCache() : Boolean{

        val cacheValue = cache.getCache(cacheKey, "false")

        return cacheValue.toBoolean()

    }

}