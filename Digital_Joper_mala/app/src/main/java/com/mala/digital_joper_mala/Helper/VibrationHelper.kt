package com.mala.digital_joper_mala.Helper

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

object VibrationHelper {

    @Composable
    fun IsVibration(context: Context) : Boolean {

        val vibrationPref = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val savedVibration = vibrationPref.getString("my_vibration", "true")

        return when(savedVibration){

            "false" -> false
            else -> true

        }

    }

}