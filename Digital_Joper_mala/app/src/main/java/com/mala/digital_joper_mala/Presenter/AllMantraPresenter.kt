package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Helper.RetryHelper
import com.mala.digital_joper_mala.Model.AllMantraModel
import com.mala.digital_joper_mala.Model.MantraItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface AllMantra{
    fun dialogStatus(value : Boolean)
    fun allMantraList(list: List<MantraItem>)
    fun favoriteMantraList(list: List<MantraItem>)
    fun searchMantraList(list: List<MantraItem>)
    fun deleteFavoriteMantraStatus(status : String)
    fun insertStatus(status : String)
    fun mantraStatus(status: String)
    fun serverStatus(isSuccess : Boolean)
    fun loading(isLoading : Boolean)
}

enum class Mantra(val value : String){

    MantraPending("mantra_pending"),
    MantraSuccess("mantra_success"),
    MantraFailed("mantra_failed")

}

class AllMantraPresenter(
    private val context : Context,
    private val view : AllMantra
) {

    private val model = AllMantraModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val retry = RetryHelper(scopeIO)
    private var currentPage = 1
    private var isLastPage = false
    private var isLoading = false
    private val mantraList = mutableListOf<MantraItem>()


    fun setAllMantraCache(value : Boolean){

        model.setAllMantraCache(value)

        view.dialogStatus(model.getAllMantraCache())

    }

    fun getAllMantraCache(){

        view.dialogStatus(model.getAllMantraCache())

    }

    fun getAllMantraFromServer(){

        view.mantraStatus(Mantra.MantraPending.value)

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

                scopeMain.launch {

                    view.mantraStatus(Mantra.MantraSuccess.value)
                    view.serverStatus(isSuccess = true)

                }


            },
            onFailed = {

                scopeMain.launch {

                    view.serverStatus(isSuccess = false)

                }

            }
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

            if (isLoading || isLastPage) return@launch

            withContext(Dispatchers.Main){

                isLoading = true
                view.loading(true)

                if (currentPage == 1) view.mantraStatus(Mantra.MantraPending.value)

            }

            val newData = model.getAllMantra(currentPage)

            withContext(Dispatchers.Main){

                if (newData.isEmpty()){

                    isLastPage = true
                    isLoading = false
                    view.loading(false)

                } else{

                    mantraList.addAll(newData)

                    if (currentPage == 1) view.mantraStatus(Mantra.MantraSuccess.value)

                    view.allMantraList(mantraList.toList())

                    isLastPage = false
                    currentPage++

                }

                isLoading = false
                view.loading(false)

                if (mantraList.isEmpty()) view.mantraStatus(Mantra.MantraFailed.value)

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