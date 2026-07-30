package com.mala.digital_joper_mala.View

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.Model.HomeData
import com.mala.digital_joper_mala.Presenter.Home
import com.mala.digital_joper_mala.Presenter.HomePresenter
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import java.util.Locale
import java.util.Locale.getDefault
import androidx.compose.ui.platform.LocalLocale
import androidx.compose.ui.text.style.TextAlign
import androidx.core.net.toUri
import coil3.compose.AsyncImage
import kotlinx.coroutines.delay


class Act_home : ComponentActivity(), Home {

    private lateinit var presenter : HomePresenter

    //init
    private val rulesList = mutableStateListOf<HomeData>()
    private val infoList = mutableStateListOf<HomeData>()
    private val pagerList = mutableStateListOf<HomeData>()
    private var serverStatus = mutableStateOf("")
    private var version = mutableStateOf("")
    private var currentVersion = mutableStateOf("")
    private var isUpdateAvailable = mutableStateOf(false)

    private companion object{

        const val APP_PACKAGE_NAME = "com.mala.digital_joper_mala"

    }

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

            presenter.appUpdate()

            try {

                currentVersion.value = packageManager.getPackageInfo(packageName, 0).versionName ?: ""

            }catch (e : PackageManager.NameNotFoundException){
                e.printStackTrace()
            }

            // Checking For App Update

            val newVersion = version.value.toDoubleOrNull()
            val currentVersion = currentVersion.value.toDoubleOrNull()

            if (newVersion != null && currentVersion != null){

                if (newVersion > currentVersion) isUpdateAvailable.value = true else isUpdateAvailable.value = false

            }

            Digital_Joper_malaTheme {
                HomeFullScreen(
                    isDark = isDark,
                    notificationClick = {},
                    addMantraClick = { IntentHelper.normalIntent(this, Act_add_mantra::class.java) },
                    settingClick = { IntentHelper.normalIntent(this, Act_setting::class.java) },
                    homeClick = {

                        rulesList.clear()
                        infoList.clear()
                        presenter.pagerDataFromServer()

                    },
                    rulesClick = {

                        pagerList.clear()
                        infoList.clear()
                        presenter.getRules()

                                 },
                    infoClick = {

                        pagerList.clear()
                        rulesList.clear()
                        presenter.dataFromServer()

                    },
                    rulesList = rulesList,
                    infoList = infoList,
                    pagerList = pagerList,
                    status = serverStatus.value,
                    isUpdateAvailable = isUpdateAvailable.value,
                    updateClick = {

                        try {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    ("https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME").toUri()
                                )
                            )
                            finishAffinity()
                        } catch (e: ActivityNotFoundException) {
                            startActivity(
                                Intent(
                                    Intent.ACTION_VIEW,
                                    ("market://details?id=$APP_PACKAGE_NAME").toUri()
                                )
                            )
                            finishAffinity()
                        }

                    }
                )

            }
        }
    }//on create===========================================

    private fun init(){

        presenter = HomePresenter(this)

    }

    override fun rulesList(list: List<HomeData>) {
        rulesList.clear()
        rulesList.addAll(list)

    }

    override fun infoList(list: List<HomeData>) {

        infoList.clear()
        infoList.addAll(list)

    }

    override fun pagerList(list: List<HomeData>) {
        pagerList.clear()
        pagerList.addAll(list)
    }

    override fun serverStatus(status: String) {
        serverStatus.value = status

    }

    override fun updateStatus(status: String) {
        version.value = status
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

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
    infoClick: () -> Unit = {},
    rulesList : List<HomeData> = emptyList(),
    infoList : List<HomeData> = emptyList(),
    pagerList: List<HomeData> = emptyList(),
    status: String = "",
    isUpdateAvailable: Boolean = false,
    updateClick: () -> Unit = {}
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

                    Home(
                        isDark = isDark,
                        pagerList = pagerList,
                        isUpdateAvailable = isUpdateAvailable,
                        updateClick = { updateClick() }
                    )

                }

                1 -> {

                    Rules(
                        list = rulesList,
                        isDark = isDark
                    )

                }

                2 -> {

                    Info(
                        status = status,
                        list = infoList,
                        isDark = isDark
                    )

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
private fun Home(
    isDark: Boolean = false,
    pagerList : List<HomeData> = emptyList(),
    isUpdateAvailable : Boolean = false,
    updateClick : () -> Unit = {}
) {

    var isUpdate by remember { mutableStateOf(false) }

    LaunchedEffect(isUpdateAvailable) {

        if (isUpdateAvailable) isUpdate = true else isUpdate = false

    }

    Box(

        modifier = Modifier
            .fillMaxSize()

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()

        ) {

            if (pagerList.isNotEmpty()){

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)

                ) {

                    PagerHelper().Pager(
                        modifier = Modifier
                            .fillMaxWidth(),
                        list = pagerList,
                        placeHolder = painterResource(R.drawable.img_loader),
                        height = 150.dp

                    )

                }//box

                Spacer(modifier = Modifier.height(5.dp))

            }

            Row(

                modifier = Modifier
                    .fillMaxWidth()

            ) {

                val mala = arrayOf("সহজ মালা", "বৈষ্ণব মালা", "শিব মালা")

                mala.forEachIndexed { index, text ->

                    Column(

                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(5.dp)

                    ) {

                        Box(

                            modifier = Modifier
                                .wrapContentWidth()
                                .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                                .clip(shape = RoundedCornerShape(12.dp))
                                .background(
                                    color = if (isDark) Color(0xFFAFADAD) else Color(
                                        0xFFFFFFFF
                                    )
                                )
                                .size(60.dp)
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)

                        ) {

                            Image( painter = painterResource(R.drawable.img_splash),
                                contentDescription = "",
                                modifier = Modifier
                                    .fillMaxSize()

                            )

                        }//image box

                        Spacer(modifier = Modifier.height(5.dp))

                        Text( text = text,
                            fontSize = 14.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterHorizontally)

                        )

                    }//column

                }//loop

            }//row

        }//column

        if (isUpdate){

            UpdateApp(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                updateClick = { updateClick() }
            )

        }

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
private fun Rules(
    list: List<HomeData> = emptyList(),
    isDark: Boolean = false
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()

    ) {

        if (list.isNotEmpty()){

            LazyColumn(

                state = rememberLazyListState(),
                modifier = Modifier
                    .fillMaxWidth()

            ) {

                items(
                    items = list,
                    //key = { it.id }
                ){

                    Item(
                        question = it.question,
                        answer = it.answer,
                        isDark = isDark
                    )

                }

                items(count = 1){

                    Spacer(modifier = Modifier.height(60.dp))

                }

            }//lazy column

        }

    }//box
    
}//fun end

@Preview(showBackground = true)
@Composable
private fun Info(
    status : String = "pending",
    list: List<HomeData> = emptyList(),
    isDark: Boolean = false
) {

    Box(

        modifier = Modifier
            .fillMaxSize()

    ) {
        

        when(status){

            "pending" -> {

                CircularProgressIndicator(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.Center),
                    color = Color(0xFF009688)
                )

            }

            "failed" -> {

                Image( painter = painterResource(R.drawable.img_empty_box),
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(90.dp)
                        .align(Alignment.Center)

                )

            }

            "success" -> {

                LazyColumn(

                    state = rememberLazyListState(),
                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    items(
                        items = list,
                        //key = { it.id }
                    ){

                        Item(
                            question = it.question,
                            answer = it.answer,
                            isDark = isDark
                        )

                    }

                    items(count = 1){

                        Spacer(modifier = Modifier.height(60.dp))

                    }

                }//lazy column

            }

        }

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun Item(
    question : String = "Test",
    answer : String = "Testttttttt",
    isDark: Boolean = false
) {

    var isAnswerVisible by remember { mutableStateOf(false) }

    val iconRotate by animateFloatAsState(
        targetValue = if (isAnswerVisible) 270f else 90f,
        label = ""
    )

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isDark) Color(0xFF7E7D7D) else Color(0xFFDEDBDB),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable { isAnswerVisible = !isAnswerVisible }
                    .padding(9.dp)

            ) {

                Text( text = question,
                    fontSize = 16.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    modifier = Modifier
                        .fillMaxWidth(0.93f)
                        .align(Alignment.CenterStart)

                )

                Icon( painter = painterResource(R.drawable.ic_right),
                    contentDescription = "",
                    tint = Color(0xFF7A7474),
                    modifier = Modifier
                        .wrapContentWidth()
                        .rotate(iconRotate)
                        .align(Alignment.CenterEnd)

                )

            }//box

            if (isAnswerVisible){

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp)

                ) {

                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                width = 1.dp,
                                color = if (isDark) Color(0xFF5E5E5E) else Color(0xFFECE9E9),
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(shape = RoundedCornerShape(12.dp))
                            .padding(12.dp)

                    ) {

                        Text(text = answer,
                            fontSize = 16.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFFC9C9C9) else Color(0xFF5B5A5A),
                            modifier = Modifier
                                .fillMaxWidth()
                        )

                    }//box

                }//box

            }

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun UpdateApp(
    modifier: Modifier = Modifier,
    updateClick : () -> Unit = {}
) {
    
    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(9.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(14.dp))
                .clip(shape = RoundedCornerShape(14.dp))
                .clickable(
                    indication = null,
                    interactionSource = null
                ){ updateClick() }
                .background(color = Color(0xFFFFFFFF))
                .padding(15.dp)

        ) {

            Image( painter = painterResource(R.drawable.img_update),
                contentDescription = "",
                modifier = Modifier
                    .wrapContentWidth()
                    .size(70.dp)
                    .align(Alignment.CenterHorizontally)

            )

            Spacer(modifier = Modifier.height(7.dp))

            Text( text = "নতুন আপডেট উপলব্ধ! আরও ভালো অভিজ্ঞতার জন্য এখনই আপডেট করুন।",
                fontSize = 17.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            )

        }//column

    }//box

}//fun end