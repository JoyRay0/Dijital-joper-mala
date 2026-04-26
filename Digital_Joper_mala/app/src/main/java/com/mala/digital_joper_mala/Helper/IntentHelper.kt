package com.mala.digital_joper_mala.Helper

import android.app.Activity
import android.content.Context
import android.content.Intent

object IntentHelper {

    fun dataIntent(
        context: Context,
        cls : Class <*>,
        key : String,
        value : String
    ){

        val intent = Intent(context, cls::class.java)
        intent.putExtra(key, value)
        context.startActivity(intent)

    }//fun end

    fun <T> normalIntent(activity: Activity, cls : Class <T>){

        activity.startActivity(Intent(activity, cls))

    }

}