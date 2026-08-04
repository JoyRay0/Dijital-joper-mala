package com.mala.digital_joper_mala.Model

import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.ApiLinkHelper
//import com.mala.digital_joper_mala.Helper.ApiLinkHelper
import com.mala.digital_joper_mala.Helper.OkHttpWrapper

data class Tracker(

    val status : String = "",
    val message : String = "",
    val data : List<TrackerData> = emptyList()

)

data class TrackerData(

    val id : Int = 0,
    val activity : String = "",
    val duration : Long = 0L
)

class TrackerModel(
    private val db : ScreenTrackerDatabase
) {

    fun sendTrackerDataToServer(
        tracker : List<TrackerData> = emptyList(),
        onSuccess : (Boolean) -> Unit = {},
        onFailed : (Boolean) -> Unit = {},
    ){

        if (tracker.isEmpty()) return

        OkHttpWrapper()
            .url(ApiLinkHelper.tracker())
            .post(tracker)
            .execute(Home::class.java, onSuccess = {

                if (it.status == "Success"){

                    onSuccess(true)

                }else{

                    onSuccess(false)

                }


            }, onFailed = {

                onFailed(it)

            })


    }

    fun insert(act : ACTIVITY, duration : Long, deviceVersion : String, deviceSDK : Int, deviceCountryCode : String){

        if (duration <= 0 || deviceVersion.isEmpty() || deviceSDK <= 0 || deviceCountryCode.isEmpty()) return

        db.insert(act, duration)

    }

    fun getAll() : List<TrackerData>{

        return db.getAll()

    }

    fun resetAll(){

        db.resetAll()

    }

}