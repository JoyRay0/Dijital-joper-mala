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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.Helper.VibrationHelper
import com.mala.digital_joper_mala.Model.ShivItem
import com.mala.digital_joper_mala.Presenter.HomePresenter
import com.mala.digital_joper_mala.Presenter.ShivMala
import com.mala.digital_joper_mala.Presenter.ShivMalaPresenter
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*


class Act_shiv_mala : ComponentActivity(), ShivMala {

    private lateinit var tracker : TrackScreen
    private lateinit var presenter : ShivMalaPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            var isDark by remember { mutableStateOf(false) }
            var isVibration by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            if (VibrationHelper.IsVibration(this)) isVibration = true else isVibration = false

            Digital_Joper_malaTheme {

                ShivMalaFullScreen(
                    isDark = isDark,
                    backClick = { finish() }
                )

            }
        }
    }//on create===========================

    private fun init(){

        tracker = TrackScreen(this)

        presenter = ShivMalaPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        tracker.start(ACTIVITY.Act_shiv_mala)

        tracker.send()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_shiv_mala)
    }

    override fun malaList(list: List<ShivItem>) {

    }

}//class===================================

@Preview(showBackground = true)
@Composable
private fun ShivMalaFullScreen(
    isDark: Boolean = false,
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

    ) { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDark) DarkBackground else LightBackground)
                .padding(innerPadding)
        ) {



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
                painter = painterResource(com.mala.digital_joper_mala.R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }

    }//box

}//fun end
