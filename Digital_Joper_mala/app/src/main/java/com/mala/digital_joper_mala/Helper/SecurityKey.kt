package com.mala.digital_joper_mala.Helper

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings

object SecurityKey {

    @SuppressLint("HardwareIds")
    fun androidID(context: Context) : String{

        return Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)

    }

}