package com.mala.digital_joper_mala.Utils

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

        val themePref = context.getSharedPreferences("my_theme", Context.MODE_PRIVATE)
        val savedTheme = themePref.getInt("app_theme", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        return when(savedTheme){

            AppCompatDelegate.MODE_NIGHT_YES -> true
            AppCompatDelegate.MODE_NIGHT_NO -> false
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM -> isSystemInDarkTheme()
            else -> false

        }

    }

}