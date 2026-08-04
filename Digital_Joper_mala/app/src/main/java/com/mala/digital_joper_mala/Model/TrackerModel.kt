package com.mala.digital_joper_mala.Model

import com.google.gson.annotations.SerializedName
import com.mala.digital_joper_mala.Database.DeviceInfoDatabase
import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.ApiLinkHelper
//import com.mala.digital_joper_mala.Helper.ApiLinkHelper
import com.mala.digital_joper_mala.Helper.OkHttpWrapper

data class Tracker(

    val status : String = "",
    val message : String = "",

    @SerializedName("android_version")
    val androidVersion : String = "",

    @SerializedName("sdk_version")
    val sdkVersion : String = "",

    @SerializedName("country_code")
    val countryCode : String = "",

    val data : List<TrackerData> = emptyList()

)

data class TrackerData(

    val id : Int = 0,
    val activity : String = "",
    val duration : Long = 0L
)

class TrackerModel(
    private val activityDB : ScreenTrackerDatabase,
    private val deviceInfoDB : DeviceInfoDatabase
) {

    fun sendTrackerDataToServer(
        tracker : Tracker? = null,
        onSuccess : (Boolean) -> Unit = {},
        onFailed : (Boolean) -> Unit = {},
    ){

        if (tracker == null) return

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

    fun insertActivityTracker(act : ACTIVITY, duration : Long){

        if (duration <= 0 ) return

        activityDB.insert(act, duration)

    }

    fun getAllActivityData() : List<TrackerData>{

        return activityDB.getAll()

    }

    fun resetAllActivity(){

        activityDB.resetAll()

    }

    fun insertDeviceInfo(androidVersion : String, sdkVersion : Int, countryCode : String){

        if (androidVersion.isEmpty() || sdkVersion <= 0 || countryCode.isEmpty()) return

        deviceInfoDB.insertDeviceInfo(androidVersion, sdkVersion, countryCode)

    }

    fun getDeviceInfo() : Tracker?{

        return deviceInfoDB.getDeviceInfo()

    }

    fun resetDeviceInfo(){

        deviceInfoDB.resetDeviceInfo()

    }

}