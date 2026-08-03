package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkToolBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightToolBar

class Act_new_feature : ComponentActivity() {

    private lateinit var tracker : TrackScreen

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            var isDark by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme {

                NewFeatureFullScreen(

                    isDark = isDark,
                    backClick = { finish() }

                )

            }
        }
    }// on create================================

    private fun init(){

        tracker = TrackScreen(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_new_feature)
    }

}//class ==========================================


@Preview(showBackground = true)
@Composable
private fun NewFeatureFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {}
) {

    //===================================================

    val textList = emptyList<String>()

    val imageList = emptyList<Int>()

    //=================================================


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

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(5.dp)

            ) {

                if (textList.isEmpty() || imageList.isEmpty()){

                    Text( text = "কোন নতুন ফিচার যুক্ত হয়নি ।",
                        fontSize = 16.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)

                    )

                }else{

                    Spacer(modifier = Modifier.height(7.dp))

                    textList.forEachIndexed { index, text ->

                        Text(text = text,
                            fontSize = 16.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .wrapContentWidth()
                                .padding(4.dp)
                                .align(Alignment.Start)
                        )

                        Spacer(modifier = Modifier.height(7.dp))

                        Image( painter = painterResource(imageList[index]),
                            contentDescription = "",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(400.dp)
                                .background(color = Color(0xFFF0CCCC))
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)

                        )
                        Spacer(modifier = Modifier.height(7.dp))

                    }

                }

            }//column

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
