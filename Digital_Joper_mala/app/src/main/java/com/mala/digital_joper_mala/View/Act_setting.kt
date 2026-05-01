package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*

class Act_setting : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDark by remember { mutableStateOf(false) }


            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme {

                SettingFullScreen(
                    isDark = isDark,
                    backClick = { finish() }
                )

            }
        }
    }//on create===============================
}//class=======================================


@Preview(showBackground = true)
@Composable
private fun SettingFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {}
) {

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() }
        ) },
        
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

        ){



        }//box

    }//scaffold

}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isDark) DarkToolBar else LightToolBar)
            .padding(7.dp)

    ) {

        IconButton(
            onClick = backClick,
            modifier = Modifier
                .wrapContentWidth()
                .clip(shape = CircleShape)
                //.background(color = Color.Green)
                .align(Alignment.CenterStart)
                .size(37.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
private fun TextButtonHelper(
    text : String = "",
    textIcon : Int = 0,
    iconSize : Dp? = null,
    textClick : () -> Unit = {},
    isDark: Boolean = false
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()

    ) { }//box
    
}//fun end