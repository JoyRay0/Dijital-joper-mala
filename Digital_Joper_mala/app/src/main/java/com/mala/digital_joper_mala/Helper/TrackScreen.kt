package com.mala.digital_joper_mala.Helper

import android.content.Context
import android.os.Build
import android.os.SystemClock
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
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

    private val db = ScreenTrackerDatabase(context)
    private val presenter = TrackerPresenter(db)

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
        val currentCountryCode = Locale.getDefault().country

        if (totalSeconds > 0L || currentAndroidVersion.isNotEmpty() || currentAndroidSdk > 0 || currentCountryCode.isNotEmpty()){

            presenter.insert(screen, totalSeconds, currentAndroidVersion, currentAndroidSdk, currentCountryCode)

        }

    }

    fun destroy(){
        presenter.onDestroy()
    }

}//class

