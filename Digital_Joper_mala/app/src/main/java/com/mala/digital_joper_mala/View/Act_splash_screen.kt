package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.tooling.preview.Preview
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme

class Act_splash_screen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Digital_Joper_malaTheme {

            }
        }
    }//on create=================================
}//class=========================================