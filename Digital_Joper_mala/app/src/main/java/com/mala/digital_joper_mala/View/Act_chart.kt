package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.ComposeHelper
import com.mala.digital_joper_mala.Helper.IntentHelper
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.Model.JopHistory
import com.mala.digital_joper_mala.Presenter.History
import com.mala.digital_joper_mala.Presenter.JopCountHistory
import com.mala.digital_joper_mala.Presenter.JopHistoryPresenter

class Act_chart : ComponentActivity(), JopCountHistory {//class======================================================

    private lateinit var tracker : TrackScreen
    private lateinit var presenter : JopHistoryPresenter

    //init

    private val historyStatus = mutableStateOf("")
    private val jopHistoryList = mutableStateListOf<JopHistory>()
    private val isPaginationLoading = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            var isDark by remember { mutableStateOf(false) }
            var reloadHistory by remember { mutableIntStateOf(0) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            LaunchedEffect(reloadHistory) {

                presenter.getAllJopCountHistory()

            }


            Digital_Joper_malaTheme {

                JopHistoryFullScreen(
                    backClick = {
                        IntentHelper.normalIntent(this, Act_home::class.java)
                        finish()
                                },
                    deleteClick = { },
                    jopHistoryList = jopHistoryList,
                    historyStatus = historyStatus.value,
                    isDark = isDark,
                    isLoading = isPaginationLoading.value,
                    onLoadMore = { reloadHistory++ }
                )

            }

            BackHandler() {

                IntentHelper.normalIntent(this, Act_home::class.java)
                finish()

            }

        }

    }// on create=============================================

    private fun init(){

        tracker = TrackScreen(this)

        presenter = JopHistoryPresenter(this, this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_chart)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun historyList(list: List<JopHistory>) {
        jopHistoryList.clear()
        jopHistoryList.addAll(list)
    }

    override fun historyStatus(status: String) {
        historyStatus.value = status
    }

    override fun singleCountHistory(count: Long) {

    }

    override fun loading(isLoading: Boolean) {
        isPaginationLoading.value = isLoading
    }


}


@Preview(showBackground = true)
@Composable
private fun JopHistoryFullScreen(
    backClick: () -> Unit = {},
    deleteClick: () -> Unit = {},
    jopHistoryList: List<JopHistory> = emptyList(),
    historyStatus : String = "",
    isDark : Boolean = false,
    isLoading : Boolean = false,
    onLoadMore : () -> Unit = {}
    ) {

    val lazyState = rememberLazyListState()

    Scaffold(
        topBar = {
            Toolbar( backClick = {backClick()},
                //deleteClick = {deleteClick()},
                isDark = isDark
            )},
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isDark) DarkStatusBar else LightStatusBar)
            .systemBarsPadding(),

        )
    { innerPadding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDark) DarkBackground else LightBackground)
                .padding(innerPadding)

        ) {

            if (jopHistoryList.isEmpty() && historyStatus == History.HistoryPending.value){

                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(9.dp)
                        .wrapContentWidth()
                        //.size(30.dp)
                        .align(Alignment.Center),
                    color = if (isDark) Color.LightGray else Color(0xFF009688)

                )

            }else if(jopHistoryList.isNotEmpty()){

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth(),
                    state = lazyState

                ) {

                    items(
                        items = jopHistoryList,
                        key = { it.id }
                    ){ it ->

                        HistoryItem(
                            modifier = Modifier.animateItem(),
                            day = it.day,
                            date = it.date,
                            count = it.count
                        )

                    }

                    items(
                        count = 1,
                        key = {"bottom_loader"}
                    ){

                        ComposeHelper().BottomLoader(

                            isLoading = isLoading,
                            onLoadMore = { onLoadMore() },
                            isDark = isDark

                        )

                    }

                }//lazyColumn

            }else{

                Text( text = "আপনার জপের যাত্রা এখনও শুরু হয়নি। জপ শুরু করুন - আপনার প্রতিটি জপের অগ্রগতি এখানে সুন্দরভাবে সংরক্ষিত থাকবে।",
                    fontSize = 17.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color.White else Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(12.dp)
                        .align(Alignment.Center)

                )

            }//condition

        }//box

    }//scaffold

}//fun end


@Preview(showBackground = true)
@Composable
private fun Toolbar(
    backClick : () -> Unit = {},
    //deleteClick : () -> Unit = {},
    isDark : Boolean = false
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

            Icon( painter = painterResource(R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }

        /*
        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd)
        ) {

            IconButton(
                onClick = deleteClick,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = CircleShape)
                    //.background(color = Color.Green)
                    .align(Alignment.CenterVertically)
                    .size(37.dp)
            ) {

                Icon( painter = painterResource(R.drawable.ic_delete),
                    contentDescription = "Back",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .wrapContentWidth()

                )

            }

        }//row

         */

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
fun HistoryItem(
    modifier: Modifier = Modifier,
    day : String = "",
    date : String = "Test",
    count : Long = 0L,
    isDark: Boolean = true
) {

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = if (isDark) Color.White.copy(alpha = 0.3f) else Color.Transparent,
                    shape = RoundedCornerShape(12.dp)
                    )
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = if (isDark) Color.DarkGray.copy(alpha = 0.8f) else Color(0xFFEEECEC))
                .padding(10.dp)
                .align(Alignment.Center)

        ) {

            Text( text = "$day : $date",
                fontSize = 16.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color.White.copy(alpha = 0.8f) else Color.DarkGray,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterStart)

            )

            Text( text = BanglaHelper.readLong(count),
                fontSize = 16.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.SemiBold,
                color = if (isDark) Color.White else Color.Black,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.CenterEnd)

            )

        }//box

    }//box

}//fun end
