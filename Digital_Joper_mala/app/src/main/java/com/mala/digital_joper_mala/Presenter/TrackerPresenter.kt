package com.mala.digital_joper_mala.Presenter

import com.mala.digital_joper_mala.Database.DeviceInfoDatabase
import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.RetryHelper
import com.mala.digital_joper_mala.Model.Tracker
import com.mala.digital_joper_mala.Model.TrackerModel
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

class TrackerPresenter(
    private val activityDB : ScreenTrackerDatabase,
    private val deviceInfoDB : DeviceInfoDatabase

    ) {

    private val model = TrackerModel(activityDB, deviceInfoDB)

    private val scopeIO = CoroutineScope(Dispatchers.IO + SupervisorJob())
    private val scopeMain = CoroutineScope(Dispatchers.Main + SupervisorJob())

    private val retryHelper = RetryHelper(scopeIO)

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

    fun updateLastOpen(){
        scopeIO.launch {

            val id = model.getDeviceId()
            val timeStamp = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())

            if (id.isEmpty()){

                model.insertDeviceId(UUID.randomUUID().toString())

            }

            model.updateLastOpen(timeStamp)

        }

    }

    fun sendTrackerDataToServer(
        onSuccessResult : (Boolean) -> Unit
    ){

        var isSuccess = false

        retryHelper.retry(
            maxTime = 3_00_000,
            delayTime = 5_000,
            request = {success, failed ->

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

                            success()

                            scopeMain.launch {

                                isSuccess = status

                            }

                        },
                        onFailed = {

                            failed()

                        }
                    )

                }

            },
            onSuccess = {


                if (isSuccess){

                    scopeIO.launch {

                        model.resetAllActivity()
                        model.resetDeviceInfo()

                    }

                }

                onSuccessResult(isSuccess)

            },
            onFailed = {}
        )

    }

    fun onDestroy(){

        scopeIO.cancel()
        scopeMain.cancel()

    }

}