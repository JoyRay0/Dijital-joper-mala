package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.ComposeHelper
import com.mala.digital_joper_mala.Helper.IntentHelper
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.Model.NotificationItem
import com.mala.digital_joper_mala.Presenter.Notification
import com.mala.digital_joper_mala.Presenter.NotificationPresenter
import com.mala.digital_joper_mala.Presenter.NotificationStatus
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkToolBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightToolBar
import com.rk_softwares.lawguidebook.Helper.ScreenSize

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
            var reloadAllNotification by remember { mutableIntStateOf(0) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )


            LaunchedEffect(reloadAllNotification) {

                presenter.getAllNotification()

            }

            Digital_Joper_malaTheme {

                NotificationFullScreen(
                    isDark = isDark,
                    backClick = {
                        IntentHelper.normalIntent(this, Act_home::class.java)
                        finishAffinity()
                                },
                    deleteAllClick = { presenter.deleteAllNotification() },
                    notificationList = notificationList,
                    notificationStatus = notificationStatus.value,
                    isPaginationLoading = isPaginationLoading.value,
                    loadMoreNotification = { reloadAllNotification++ },
                    notificationClick = { presenter.hasSeenNotification(it) }
                )

            }

            BackHandler() {

                IntentHelper.normalIntent(this, Act_home::class.java)
                finishAffinity()

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
    notificationStatus : String = "notification_pending",
    isPaginationLoading : Boolean = false,
    loadMoreNotification : () -> Unit = {},
    notificationClick: (String) -> Unit = {}
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

            if (notificationList.isEmpty() && notificationStatus == NotificationStatus.Pending.value){

                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(ScreenSize().responsivePadding(9, 12, 15))
                        .wrapContentWidth()
                        //.size(30.dp)
                        .align(Alignment.Center),
                    color = if (isDark) Color.LightGray else Color(0xFF009688)

                )

            }else if (notificationList.isNotEmpty()){

                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {

                    items(
                        items = notificationList,
                        key = {it.id}
                    ){

                        NotificationItem(
                            title = it.title,
                            description = it.description,
                            isDark = isDark,
                            isShowed = it.isShowed,
                            notificationClick = { notificationClick(it.title) }
                        )

                    }

                    items(1, key = {"botton_loader"}){

                        ComposeHelper().BottomLoader(
                            isLoading = isPaginationLoading,
                            onLoadMore = { loadMoreNotification() },
                            isDark = isDark
                        )

                    }

                }

            }else{

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)

                ) {

                    Image( painter = painterResource(R.drawable.img_notification),
                        contentDescription = "notification",
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(ScreenSize().responsiveImageSize(70, 90, 110))
                            .align(Alignment.CenterHorizontally)

                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text( text = "কোন নোটিফিকেশন নেই।",
                        fontSize = ScreenSize().responsiveTextSize(15, 17, 19),
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)

                    )

                }//column

            }//condition

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


@Preview(showBackground = true)
@Composable
fun NotificationItem(
    title : String = "TTTTT",
    description : String = "DDDDDD",
    isDark: Boolean = false,
    isShowed : Boolean = false,
    notificationClick : () -> Unit = {}
) {

    var isDescription by remember { mutableStateOf(true) }
    val animatedIcon = animateFloatAsState(
        targetValue = if (isDescription) 180f else 360f
    )

    Box(

        modifier = Modifier
            .fillMaxWidth()
            //.background(color = if (isDark) Color.Black else Color.White)

    ) { 
        
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isDark) Color.LightGray.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.3f),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(RoundedCornerShape(12.dp))
                    .clickable{
                        notificationClick()
                        isDescription = !isDescription
                    }
                    .padding(ScreenSize().responsivePadding(10, 12, 14))

            ) {

                Text( text = title,
                    fontSize = ScreenSize().responsiveTextSize(16, 18, 20),
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = if (isShowed) FontWeight.Normal else FontWeight.SemiBold,
                    color =
                        if (isDark){

                            if (isShowed){

                                Color(0xFFDADADA)

                            }else{

                                Color(0xFFFFFFFF)

                            }

                        }else{

                            if (isShowed){

                                Color(0xFF3F3E3E)

                            }else {

                                Color(0xFF000000)

                            }}
                    ,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterStart)

                )

                Icon( painter = painterResource(R.drawable.ic_down),
                    contentDescription = "",
                    tint = if (isDark) Color(0xFFE5E5E5) else Color(0xFF343434),
                    modifier = Modifier
                        .wrapContentWidth()
                        .rotate(animatedIcon.value)
                        .align(Alignment.CenterEnd)

                )

            }//box


            if (isDescription){

                Box(

                    modifier = Modifier
                        .padding(9.dp)
                        .fillMaxWidth()
                        .border(
                            width = 1.dp,
                            color = if (isDark) Color.LightGray.copy(alpha = 0.3f) else Color.Gray.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        .clip(RoundedCornerShape(12.dp))
                        .clickable{
                            isDescription = !isDescription
                        }
                        .padding(ScreenSize().responsivePadding(10, 12, 14))

                ) {

                    Text( text = description,
                        fontSize = ScreenSize().responsiveTextSize(15, 17, 19),
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFE1E1E1) else Color(0xFF424242),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterStart)

                    )

                }//box

            }//condition

        }//column
        
    }//box
    
}//fun end