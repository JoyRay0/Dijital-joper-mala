package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Database.DeviceInfoDatabase
import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Model.Tracker
import com.mala.digital_joper_mala.Model.TrackerModel
import kotlinx.coroutines.*

class TrackerPresenter(
    private val activityDB : ScreenTrackerDatabase,
    private val deviceInfoDB : DeviceInfoDatabase

    ) {

    private val model = TrackerModel(activityDB, deviceInfoDB)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    fun insertActivityData(act : ACTIVITY, duration : Long){

        scopeIO.launch {

            model.insertActivityTracker(act, duration)

        }

    }

    fun insertDeviceInfo(androidVersion : String, sdkVersion : Int, countryCode : String){

        scopeIO.launch {

            model.insertDeviceInfo(androidVersion, sdkVersion, countryCode)

        }

    }

    fun sendTrackerDataToServer(){

        scopeIO.launch {

            val activityData = model.getAllActivityData()
            val deviceData = model.getDeviceInfo()

            model.sendTrackerDataToServer(
                tracker = Tracker(
                    androidVersion = deviceData!!.androidVersion,
                    sdkVersion = deviceData.sdkVersion,
                    countryCode = deviceData.countryCode,
                    data = activityData
                ),

                onSuccess = { status ->

                    if (status){

                        model.resetAllActivity()
                        model.resetDeviceInfo()

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