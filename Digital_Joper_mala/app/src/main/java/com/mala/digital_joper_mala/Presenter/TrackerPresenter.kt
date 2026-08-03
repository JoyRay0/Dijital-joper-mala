package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Model.TrackerData
import com.mala.digital_joper_mala.Model.TrackerModel
import kotlinx.coroutines.*

class TrackerPresenter(
    private val db : ScreenTrackerDatabase
) {

    private val model = TrackerModel(db)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insert(act : ACTIVITY, duration : Long, deviceVersion : String, deviceSDK : Int, deviceCountryCode : String){

        scopeIO.launch {

            model.insert(act, duration, deviceVersion, deviceSDK, deviceCountryCode)

        }

    }

    fun sendTrackerDataToServer(){

        scopeIO.launch {

            val data = model.getAll()

            model.sendTrackerDataToServer(
                tracker = data,

                onSuccess = { status ->

                    if (status){

                        model.resetAll()

                    }

                },
                onFailed = {}
            )

        }

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}