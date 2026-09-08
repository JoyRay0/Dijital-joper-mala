package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
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
import com.mala.digital_joper_mala.Model.NotificationItem
import com.mala.digital_joper_mala.Presenter.Notification
import com.mala.digital_joper_mala.Presenter.NotificationPresenter
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkToolBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightToolBar

class Act_notification : ComponentActivity(), Notification {//class====================================

    private lateinit var presenter : NotificationPresenter
    private lateinit var tracker : TrackScreen

    //init
    private var notificationStatus = mutableStateOf("")
    private var isPaginationLoading = mutableStateOf(false)
    private val notificationList = mutableStateListOf<NotificationItem>()

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

                NotificationFullScreen(
                    isDark = isDark,
                    backClick = {},
                    deleteAllClick = {}
                )


            }
        }
    }//on create============================

    private fun init(){

        presenter = NotificationPresenter(this, this)
        tracker = TrackScreen(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_notification)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()

    }

    override fun notificationList(list: List<NotificationItem>) {
        notificationList.clear()
        notificationList.addAll(list)
    }

    override fun notificationStatus(status: String) {
        notificationStatus.value = status
    }

    override fun notificationLoading(isLoading: Boolean) {
        isPaginationLoading.value = isLoading
    }

}

@Preview(showBackground = true)
@Composable
fun NotificationFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    deleteAllClick: () -> Unit = {},
    notificationList : List<NotificationItem> = emptyList(),
    notificationStatus : String = "",
    isPaginationLoading : Boolean = false
) {

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() },
            deleteAllClick = { deleteAllClick() }

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
    deleteAllClick : () -> Unit = {}
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

        IconButton(
            onClick = deleteAllClick,
            modifier = Modifier
                .wrapContentWidth()
                .clip(shape = CircleShape)
                //.background(color = Color.Green)
                .align(Alignment.CenterEnd)
                .size(37.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_delete),
                contentDescription = "delete",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }

    }//box

}//fun end