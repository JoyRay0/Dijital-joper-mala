package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*


class Act_home : ComponentActivity() {
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
                HomeFullScreen(
                    isDark = isDark,
                    notificationClick = {},
                    addMantraClick = { IntentHelper.normalIntent(this, Act_add_mantra::class.java) },
                    settingClick = { IntentHelper.normalIntent(this, Act_setting::class.java) },
                    homeClick = {},
                    rulesClick = {},
                    infoClick = {}
                )

            }
        }
    }//on create===========================================
}//class===================================================

@Preview(showBackground = true)
@Composable
private fun HomeFullScreen(
    isDark : Boolean = false,
    notificationClick: () -> Unit = {},
    addMantraClick: () -> Unit = {},
    settingClick: () -> Unit = {},
    homeClick: () -> Unit = {},
    rulesClick: () -> Unit = {},
    infoClick: () -> Unit = {}
) {

    var index = remember { mutableStateOf(0) }


    Scaffold(

        topBar = { Toolbar(
            isDark = isDark,
            notificationClick = { notificationClick() },
            addMantraClick = { addMantraClick() },
            settingClick = { settingClick() }
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

            LaunchedEffect(index.value) {

                when(index.value){

                    0 -> homeClick()

                    1 -> rulesClick()

                    2 -> infoClick()

                }

            }

            when(index.value){

                0 -> {

                    Home()

                }

                1 -> {

                    Rules()

                }

                2 -> {

                    Info()

                }

            }

            BottonNav(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                isDark = isDark,
                bottomIndex = { index.value = it }
            )

        }//box

    }//scaffold
    
}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark: Boolean = false,
    notificationClick : () -> Unit = {},
    addMantraClick : () -> Unit = {},
    settingClick : () -> Unit = {},
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .background(color = if (isDark) DarkToolBar else LightToolBar)
            .padding(7.dp)

    ) {

        Text( text = "জপ মালা",
            fontSize = 19.sp,
            fontFamily = BanglaHelper.banglaFont(),
            fontWeight = FontWeight.Bold,
            color = Color(0xFFFFFFFF),
            modifier = Modifier
                .wrapContentWidth()
                .padding(start = 7.dp)
                .align(Alignment.CenterStart)

        )

        Row(

            modifier = Modifier
                .wrapContentWidth()
                //.padding(5.dp)
                .align(Alignment.CenterEnd)

        ) {

            val icons = arrayOf(R.drawable.ic_notifications, R.drawable.ic_add, R.drawable.ic_setting)

            icons.forEachIndexed { index, icon ->

                Box(

                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(7.dp)

                ) {

                    IconButton(

                        onClick = {

                            when(index){

                                0 -> notificationClick()
                                1 -> addMantraClick()
                                2 -> settingClick()

                            }

                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(30.dp)

                    ) {

                        Icon( painter = painterResource(icon),
                            contentDescription = null,
                            tint = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(22.dp)

                        )

                    }

                }//box

            }//loop

        }//row

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun BottonNav(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    bottomIndex : (Int) -> Unit = {}
) {

    var selectedIndex = remember { mutableStateOf(0) }

    LaunchedEffect(selectedIndex.value) {

        bottomIndex(selectedIndex.value)

    }

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp)

    ){

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = if (isDark) Color(0xFF605B5B) else Color(0xFF0AF1DB))
                .padding(7.dp)

        ) {

            val icons = arrayOf(R.drawable.ic_home, R.drawable.ic_rules, R.drawable.ic_list)

            icons.forEachIndexed { index, icon ->

                Row(

                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .align(Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.Absolute.Center

                ) {

                    Box(

                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = RoundedCornerShape(20.dp))
                            .clickable {

                                selectedIndex.value = index

                            }
                            .background(color = if (selectedIndex.value == index) Color(0xFF03A9F4) else Color.Transparent)
                            .padding(start = 17.dp, end = 17.dp, top = 4.dp, bottom = 4.dp)

                    ) {

                        Icon( painter = painterResource(icon),
                            contentDescription = null,
                            tint = if (selectedIndex.value == index) {
                                Color(0xFFFCFCFC)
                            } else{

                                if (isDark) Color(0xFFC7C7C7) else Color(0xFF626262)

                            },
                            modifier = Modifier
                                .wrapContentWidth()

                        )

                    }//box

                }//box

            }//loop

        }//row

    }//box
    
}//fun end


@Preview(showBackground = true)
@Composable
private fun Home() {
    
}//fun end


@Preview(showBackground = true)
@Composable
private fun Rules() {
    
}//fun end

@Preview(showBackground = true)
@Composable
private fun Info() {
    
}//fun end