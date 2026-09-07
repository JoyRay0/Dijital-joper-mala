package com.mala.digital_joper_mala.Presenter

import android.content.Context
import com.mala.digital_joper_mala.Model.EasyMalaItem
import com.mala.digital_joper_mala.Model.EasyMalaModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface EasyMala{

    fun favoriteMantraList (list: List<EasyMalaItem>)
    fun userMantraList(list: List<EasyMalaItem>)
    fun lastCountCache (value : String)
    fun countLimit(limit: String)
    fun loading(isLoading : Boolean)

}

class EasyMalaPresenter(
    private val context: Context,
    private val view : EasyMala
) {

    private val model = EasyMalaModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var favoriteMantraCurrentPage = 1
    private var userMantraCurrentPage = 1
    private var favoriteMantraLoading = false
    private var userMantraLoading = false
    private var favoriteMantraLastPage = false
    private var userMantraLastPage = false
    private val favoriteMantraList = mutableListOf<EasyMalaItem>()
    private val userMantraList = mutableListOf<EasyMalaItem>()


    fun setLastCountCache(value : String){

        model.setLastCountCache(value)

    }

    fun getLastCountCache(){

        view.lastCountCache(model.getLastCountCache())

    }

    fun setCountLimit(value: String){

        model.setCountLimit(value)

        view.countLimit(model.getCountLimit())

    }

    fun getCountLimit(){

        view.countLimit(model.getCountLimit())

    }

    fun getAllFavoriteMantra(){

        scopeIO.launch {

            if (favoriteMantraLoading || favoriteMantraLastPage) return@launch

            withContext(Dispatchers.Main){

                favoriteMantraLoading = true
                view.loading(true)

            }

            val newData = model.getFavoriteMantra(favoriteMantraCurrentPage)

            withContext(Dispatchers.Main){

                if (newData.isEmpty()){

                    favoriteMantraLastPage = true

                }else{

                    favoriteMantraList.addAll(newData)

                    view.favoriteMantraList(favoriteMantraList.toList())

                    favoriteMantraLastPage = false
                    favoriteMantraCurrentPage++

                }

                favoriteMantraLoading = false
                view.loading(false)

            }

        }

    }

    fun getAllUserMantra(){

        scopeIO.launch {

            if (userMantraLoading || userMantraLastPage) return@launch

            withContext(Dispatchers.Main){

                userMantraLoading = true
                view.loading(true)

            }

            val newData = model.getUserMantra(userMantraCurrentPage)

            withContext(Dispatchers.Main){

                if (newData.isEmpty()){

                    userMantraLastPage = true

                }else{

                    userMantraList.addAll(newData)

                    view.userMantraList(userMantraList.toList())

                    userMantraLastPage = false
                    userMantraCurrentPage++

                }

                userMantraLoading = false
                view.loading(false)

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}