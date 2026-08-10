package com.mala.digital_joper_mala.Model

import android.content.Context
import android.util.Log
import com.mala.digital_joper_mala.Database.AllMantraDatabase
import com.mala.digital_joper_mala.Helper.ApiLinkHelper
import com.mala.digital_joper_mala.Helper.CacheHelper_
import com.mala.digital_joper_mala.Helper.OkHttpWrapper
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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

    private val dialogCacheKey = "all_mantra"
    private val serverCacheKey = "server_date"

    fun setAllMantraCache(value : Boolean){

        cache.setCache(dialogCacheKey, value.toString())

    }

    fun getAllMantraCache() : Boolean{

        val cacheValue = cache.getCache(dialogCacheKey, "false")

        return cacheValue.toBoolean()

    }

    fun getAllMantraFromServer(
        onSuccess : (Boolean) -> Unit,
        onFailed : (Boolean) -> Unit
    ){

        val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
        val lastSyncDate = cache.getCache(serverCacheKey, "")

        if (today == lastSyncDate){

            onSuccess(true)
            return
        }

        OkHttpWrapper()
            .url(ApiLinkHelper.mantra())
            .execute(Mantra::class.java, onSuccess = {

                if (it.status == "Success"){

                    db.allMantraInsert(it.data, onSuccess = { isResult ->

                        if (isResult) cache.setCache(serverCacheKey, today)

                    })

                    onSuccess(true)

                }else{

                    onFailed(true)

                }

            }, onFailed = {

                onFailed(it)

            })

    }

    fun searchAllMantra(title: String) : List<MantraItem>{

        if (title.isEmpty()) return emptyList()

        return db.searchALlMantra(title)

    }

    fun getAllMantra() : List<MantraItem>{

        return db.getAllMantra()

    }

    fun favoriteInsert(title: String, mantra : String){

        if (title.isEmpty() || mantra.isEmpty()) return

        db.favoriteInsert(title, mantra)

    }

    fun getFavoriteMantra() : List<MantraItem>{

        return db.getFavoriteMantra()

    }

    fun deleteFavoriteMantra(mantra: String) : Boolean{

        if (mantra.isEmpty()) return false

        return db.deleteFavoriteMantra(mantra)

    }

}