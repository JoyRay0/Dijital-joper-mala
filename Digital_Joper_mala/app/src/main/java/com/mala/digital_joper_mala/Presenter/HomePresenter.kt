package com.mala.digital_joper_mala.Presenter

import android.content.Context
import android.util.Log
import com.mala.digital_joper_mala.Helper.RetryHelper
import com.mala.digital_joper_mala.Model.HomeData
import com.mala.digital_joper_mala.Model.HomeModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

interface Home{

    fun rulesList(list : List<HomeData>)
    fun infoList(list : List<HomeData>)
    fun pagerList(list: List<HomeData>)
    fun serverStatus(status : String)
    fun updateStatus(status : String)
    fun mantraList(list: List<HomeData>)
    fun mantraStatus(status: String)

}

enum class HomeStatus(val value : String){

    Pending("pending"),
    Success("success"),
    Failed("failed"),

    MantraPending("mantra_pending"),
    MantraSuccess("mantra_success"),
    MantraFailed("mantra_failed"),

}

class HomePresenter(
    private val view : Home,
    private val context: Context
) {

    private val model = HomeModel(context)
    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val retryHelper = RetryHelper(scopeIO)

    fun dataFromServer(){

        view.serverStatus(HomeStatus.Pending.value)

        val infoList : MutableList<HomeData> = mutableListOf()

        retryHelper.retry(
            maxTime = 3_00_000,
            delayTime = 5_000,
            request = { success, failed ->

                scopeIO.launch {

                    model.dataFromServer(onSuccess = {

                        scopeMain.launch {

                            infoList.clear()
                            infoList.addAll(it)

                        }

                        success()

                    }, onFailed = {

                        failed()

                    })

                }

            },
            onSuccess = {

                scopeMain.launch {

                    if (infoList.isNotEmpty()){

                        view.serverStatus(HomeStatus.Success.value)
                        view.infoList(infoList)

                    }else{

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            },
            onFailed = {

                scopeMain.launch {

                    view.serverStatus(HomeStatus.Failed.value)

                }

            }
        )

    }

    fun pagerDataFromServer(){

        view.serverStatus(HomeStatus.Pending.value)

        val pagerList : MutableList<HomeData> = mutableListOf()

        retryHelper.retry(
            maxTime = 3_00_000,
            delayTime = 5_000,
            request = {success, failed ->

                scopeIO.launch {

                    model.pagerDataFromServer(onSuccess = {

                        scopeMain.launch {

                            pagerList.clear()
                            pagerList.addAll(it)

                        }

                        success()

                    }, onFailed = {

                        failed()

                    })

                }

            },
            onSuccess = {

                scopeMain.launch {

                    if (pagerList.isNotEmpty()){

                        view.serverStatus(HomeStatus.Success.value)
                        view.pagerList(pagerList)

                    }else{

                        view.serverStatus(HomeStatus.Failed.value)

                    }

                }

            },
            onFailed = {

                scopeMain.launch {

                    view.serverStatus(HomeStatus.Failed.value)

                }

            }
        )

    }

    fun appUpdate(){

        var version = ""

        retryHelper.retry(
            maxTime = 3_00_000,
            delayTime = 5_000,
            request = {success, failed ->

                scopeIO.launch {

                    model.appUpdate(onSuccess = {

                        scopeMain.launch {

                            version = it

                        }

                        success()

                    }, onFailed = {

                        failed()

                    })

                }

            },
            onSuccess = {

                scopeMain.launch {

                    if (version.isNotEmpty()){

                        view.updateStatus(version)

                    }

                }

            },
            onFailed = {

                scopeMain.launch {

                    view.updateStatus("0.0")

                }

            }
        )

    }

    fun getRules(){

        val data = model.getRules()


        if (data.isNotEmpty()){

            scopeMain.launch {

                view.rulesList(data)

            }

        }

    }

    fun getAllMantra(){

        view.mantraStatus(HomeStatus.MantraPending.value)

        scopeIO.launch {

            val data = model.getAllMantra()

            withContext(Dispatchers.Main){

                if (data.isEmpty()){

                    view.mantraStatus(HomeStatus.MantraFailed.value)

                }else{

                    view.mantraStatus(HomeStatus.MantraSuccess.value)
                    view.mantraList(data)

                }

            }

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }


}