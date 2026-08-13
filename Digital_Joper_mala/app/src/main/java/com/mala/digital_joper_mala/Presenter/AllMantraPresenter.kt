package com.mala.digital_joper_mala.Presenter

import android.content.Context
import android.util.Log
import com.mala.digital_joper_mala.Helper.RetryHelper
import com.mala.digital_joper_mala.Helper.ShortMessageHelper
import com.mala.digital_joper_mala.Model.AllMantraModel
import com.mala.digital_joper_mala.Model.MantraItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.time.Duration.Companion.milliseconds

interface AllMantra{
    fun dialogStatus(value : Boolean)
    fun allMantraList(list: List<MantraItem>)
    fun favoriteMantraList(list: List<MantraItem>)
    fun searchMantraList(list: List<MantraItem>)
    fun deleteFavoriteMantraStatus(status : String)
    fun insertStatus(status : String)
}

class AllMantraPresenter(
    private val context : Context,
    private val view : AllMantra
) {

    private val model = AllMantraModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val retry = RetryHelper(scopeIO)


    fun setAllMantraCache(value : Boolean){

        model.setAllMantraCache(value)

        view.dialogStatus(model.getAllMantraCache())

    }

    fun getAllMantraCache(){

        view.dialogStatus(model.getAllMantraCache())

    }

    fun getAllMantraFromServer(){

        retry.retry(
            maxTime = 1_20_000,
            delayTime = 5_000,
            request = { success , failed ->

                scopeIO.launch {

                    model.getAllMantraFromServer(onSuccess = {

                        if (it){

                            success()

                        }

                    }, onFailed = {

                        failed()

                    })

                }


            },
            onSuccess = {

                val data = model.getAllMantra()

                scopeMain.launch {

                    view.allMantraList(data)

                }

            },
            onFailed = {}
        )



    }

    fun searchMantraInAllMantra(title: String){

        scopeIO.launch {

            val data = model.searchAllMantra(title)

            withContext(Dispatchers.Main){

                view.searchMantraList(data)

            }

        }

    }

    fun getAllMantra(){

        scopeIO.launch {

            val data = model.getAllMantra()

            withContext(Dispatchers.Main){

                view.allMantraList(data)

            }

        }

    }

    fun favoriteMantraInsert(title : String, mantra : String){

        scopeIO.launch {

            model.favoriteInsert(title, mantra, isInserted = {

                scopeMain.launch {

                    if (it){

                        view.insertStatus("সেভ হয়েছে")

                    }else{

                        view.insertStatus("সেভ হয়নি")

                    }

                }

            })

            val data = model.getFavoriteMantra()

            withContext(Dispatchers.Main){

                view.favoriteMantraList(data)

            }

        }

    }

    fun getAllFavoriteMantra(){

        scopeIO.launch {

            val data = model.getFavoriteMantra()

            withContext(Dispatchers.Main){

                view.favoriteMantraList(data)

            }

        }

    }

    fun deleteFavoriteMantra(mantra: String){

        scopeIO.launch {

            val isDeleted = model.deleteFavoriteMantra(mantra)

            val data = model.getFavoriteMantra()

            withContext(Dispatchers.Main){

                if (isDeleted){

                    view.favoriteMantraList(data)

                    view.deleteFavoriteMantraStatus("ডিলিট হয়েছে")

                }else{

                    view.deleteFavoriteMantraStatus("ডিলিট হয়নি")

                }

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}