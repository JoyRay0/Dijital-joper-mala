package com.mala.digital_joper_mala.Helper

import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

object ThemeHelper {

    @Composable
    fun SystemUi( statusBarColor : Color, navColor : Color, darkIcons : Boolean){

        val systemUi = rememberSystemUiController()

        systemUi.setStatusBarColor(
            color = statusBarColor,
            darkIcons = darkIcons
        )

        systemUi.setNavigationBarColor(
            color = navColor,
            darkIcons = darkIcons
        )

    }

    @Composable
    fun isDarkTheme(context: Context) : Boolean{

        val themePref = context.getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val savedTheme = themePref.getString("my_theme", "0")

        return when(savedTheme){

            "0" -> isSystemInDarkTheme()
            "1" -> true
            "2" -> false

            else -> false
        }

    }


    fun applyTheme(context: Context){

        val themePref = context.getSharedPreferences("Theme", Context.MODE_PRIVATE)
        val savedTheme = themePref.getString("my_theme", "0")

        val mode = when(savedTheme){

            "0" -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
            "1" -> AppCompatDelegate.MODE_NIGHT_YES
            else -> AppCompatDelegate.MODE_NIGHT_NO

        }
        AppCompatDelegate.setDefaultNightMode(mode)

    }

}