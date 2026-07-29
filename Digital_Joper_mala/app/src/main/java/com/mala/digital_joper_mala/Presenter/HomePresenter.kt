package com.mala.digital_joper_mala.Presenter

import android.util.Log
import com.mala.digital_joper_mala.Model.HomeData
import com.mala.digital_joper_mala.Model.HomeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

interface Home{

    fun rulesList(list : List<HomeData>)
    fun infoList(list : List<HomeData>)
    fun pagerList(list: List<HomeData>)
    fun serverStatus(status : String)

}

enum class HomeStatus(val value : String){

    Pending("pending"),
    Success("success"),
    Failed("failed")

}

class HomePresenter(
    private val view : Home
) {

    private val model = HomeModel()

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun dataFromServer(){

        view.serverStatus(HomeStatus.Pending.value)

        scopeIO.launch {

            model.dataFromServer(onSuccess = {

                scopeMain.launch {

                    if (it.isNotEmpty()){

                        view.serverStatus(HomeStatus.Success.value)
                        view.infoList(it)

                    }else{

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            }, onFailed = {

                if (it){

                    scopeMain.launch {

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            }, onError = {

                Log.d("err", it)

            })

        }

    }

    fun pagerDataFromServer(){

        view.serverStatus(HomeStatus.Pending.value)

        scopeIO.launch {

            model.pagerDataFromServer(onSuccess = {

                scopeMain.launch {

                    if (it.isNotEmpty()){

                        view.serverStatus(HomeStatus.Success.value)
                        view.pagerList(it)

                    }else{

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            }, onFailed = {

                if (it){

                    scopeMain.launch {

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            })

        }

    }

    fun getRules(){

        val data = model.getRules()


        if (data.isNotEmpty()){

            scopeMain.launch {

                view.rulesList(data)

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }


}