package com.mala.digital_joper_mala.Helper

import android.app.Activity
import android.content.Context
import android.content.Intent

object IntentHelper {

    fun <T> dataIntent(
        context: Context,
        cls : Class <T>,
        key : String,
        value : String
    ){

        val intent = Intent(context, cls)
        intent.putExtra(key, value)
        context.startActivity(intent)

    }//fun end

    fun <T> normalIntent(activity: Activity, cls : Class <T>){

        activity.startActivity(Intent(activity, cls))

    }

}