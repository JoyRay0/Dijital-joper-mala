package com.mala.digital_joper_mala.View

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import kotlinx.coroutines.delay

class Act_splash_screen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        ThemeHelper.applyTheme(this)

        setContent {

            var isDark by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme {

                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){

                    SplashFullScreen( isDark = isDark)

                }

                LaunchedEffect(Unit) {

                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S){

                        delay(1500)

                    }

                    IntentHelper.normalIntent(this@Act_splash_screen, Act_Home_All_Mala::class.java)
                    finishAffinity()

                }

            }
        }
    }//on create=================================
}//class=========================================

@Preview(showBackground = true)
@Composable
private fun SplashFullScreen(
    isDark : Boolean = false
) {

    Scaffold(

        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isDark) DarkStatusBar else LightStatusBar)
            .systemBarsPadding()

    ) {innerPadding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDark) DarkBackground else LightBackground)
                .padding(innerPadding)

        ) {

            Image( painter = painterResource(R.drawable.img_splash),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .size(100.dp)
                    .align(Alignment.Center)

            )

        }//box

    }//scaffold

}//fun end