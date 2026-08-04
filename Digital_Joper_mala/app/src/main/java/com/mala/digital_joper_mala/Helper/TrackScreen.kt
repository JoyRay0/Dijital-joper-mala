package com.mala.digital_joper_mala.Helper

import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.telephony.TelephonyManager
import androidx.core.content.ContextCompat.getSystemService
import com.mala.digital_joper_mala.Database.DeviceInfoDatabase
import com.mala.digital_joper_mala.Database.ScreenTrackerDatabase
import com.mala.digital_joper_mala.Presenter.TrackerPresenter
import java.util.Locale

enum class ACTIVITY(val value : String){

    Act_home("act_home"),
    Act_add_mantra("act_add_mantra"),
    Act_chart("act_chart"),
    Act_new_feature("act_new_feature"),
    Act_setting("act_setting"),
    Act_easy_mala("act_easy_mala"),
    Act_boisnob_mala("act_boisnob_mala"),
    Act_shiv_mala("act_shiv_mala"),
    Act_custom_mala("act_custom_mala"),
    Act_all_mantra("act_all_mantra"),
    Act_jopa_history("act_jopa_history"),
    Act_notification("act_notification"),
    Act_all_mantra_search("act_all_mantra_search"),
    Act_webview("act_webview")

}//enum

class TrackScreen(
    val context : Context
){

    private val activityDB = ScreenTrackerDatabase(context)
    private val deviceInfoDB = DeviceInfoDatabase(context)
    private val presenter = TrackerPresenter(activityDB, deviceInfoDB)

    private var startTime = 0L

    fun send(){

        presenter.sendTrackerDataToServer()

    }

    fun start(){

        startTime = SystemClock.elapsedRealtime()

    }

    fun stop(screen : ACTIVITY){

        val totalSeconds = (SystemClock.elapsedRealtime() - startTime) / 1000

        val currentAndroidVersion = Build.VERSION.RELEASE
        val currentAndroidSdk = Build.VERSION.SDK_INT

        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        val currentCountryCode = telephonyManager.networkCountryIso.ifEmpty { Locale.getDefault().displayCountry }.uppercase()

        if (totalSeconds > 0L){

            presenter.insertActivityData(screen, totalSeconds)

        }

        if (currentAndroidVersion.isNotEmpty() && currentAndroidSdk > 0 && currentCountryCode.isNotEmpty()){

            presenter.insertDeviceInfo(androidVersion = currentAndroidVersion, sdkVersion = currentAndroidSdk, countryCode = currentCountryCode)

        }

    }

    fun destroy(){
        presenter.onDestroy()
    }

}//class

