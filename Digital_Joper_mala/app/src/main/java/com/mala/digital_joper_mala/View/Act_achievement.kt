package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.Model.Achievement
import com.mala.digital_joper_mala.Presenter.AchievementPresenter
import com.mala.digital_joper_mala.Presenter.AchievementStatus
import com.mala.digital_joper_mala.Presenter.Achievements
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import com.rk_softwares.lawguidebook.Helper.ScreenSize

class Act_achievement : ComponentActivity(), Achievements {//class===========================================

    private lateinit var tracker : TrackScreen
    private lateinit var presenter : AchievementPresenter

    //init
    private val achievementList = mutableStateListOf<Achievement>()
    private val achievementStatus = mutableStateOf("")

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

            presenter.getAllAchievements()

            Digital_Joper_malaTheme {

                AchievementFullScreen(
                    isDark = isDark,
                    backClick = {
                        IntentHelper.normalIntent(this, Act_home::class.java)
                        finish()
                    },
                    achievementStatus = achievementStatus.value,
                    achievementsList = achievementList
                )

            }

            BackHandler() {

                IntentHelper.normalIntent(this, Act_home::class.java)
                finish()

            }
        }
    }//on create====================================

    private fun init(){

        tracker = TrackScreen(this)

        presenter = AchievementPresenter(this, this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start(ACTIVITY.Act_achievement)

        tracker.send()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_achievement)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun achievementList(list: List<Achievement>) {
        super.achievementList(list)

        achievementList.clear()
        achievementList.addAll(list)
    }

    override fun achievementStatus(status: String) {
        super.achievementStatus(status)

        achievementStatus.value = status

    }

}

@Preview(showBackground = true)
@Composable
private fun AchievementFullScreen(
    isDark: Boolean = false,
    backClick: () -> Unit = {},
    achievementStatus : String = "",
    achievementsList : List<Achievement> = emptyList()
) {

    val lazyState = rememberLazyListState()
    var isAchievementLoaded by remember { mutableStateOf(false) }

    val achievementEasyMalaList = remember { mutableStateListOf<Achievement>() }
    val achievementBoishnobMalaList = remember { mutableStateListOf<Achievement>() }
    val achievementShivMalaList = remember { mutableStateListOf<Achievement>() }

    LaunchedEffect(achievementStatus) {

        if (achievementStatus == AchievementStatus.Pending.value || isAchievementLoaded) return@LaunchedEffect

        achievementEasyMalaList.clear()
        achievementBoishnobMalaList.clear()
        achievementShivMalaList.clear()

        isAchievementLoaded = false

        for ( achievement in achievementsList){

            when (achievement.malaName) {
                "easy_mala" -> {

                    achievementEasyMalaList.add(
                        Achievement(
                            id = achievement.id,
                            malaName = achievement.malaName,
                            achievementCount = achievement.achievementCount,
                            isCompleted = true
                        )
                    )

                }
                "shiv_mala" -> {

                    achievementShivMalaList.add(
                        Achievement(
                            id = achievement.id,
                            malaName = achievement.malaName,
                            achievementCount = achievement.achievementCount,
                            isCompleted = true
                        )
                    )

                }
                else -> {

                    achievementBoishnobMalaList.add(
                        Achievement(
                            id = achievement.id,
                            malaName = achievement.malaName,
                            achievementCount = achievement.achievementCount,
                            isCompleted = true
                        )
                    )

                }
            }

        }//loop

        isAchievementLoaded = true

    }

    Scaffold(

        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() },
            isAnyDialogVisible = false
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

            if (achievementStatus == AchievementStatus.Pending.value || !isAchievementLoaded){

                CircularProgressIndicator(
                    modifier = Modifier
                        .padding(9.dp)
                        .wrapContentWidth()
                        //.size(30.dp)
                        .align(Alignment.Center),
                    color = if (isDark) Color.LightGray else Color(0xFF009688)

                )

            }else{

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth(),
                    state = lazyState

                ) {

                    /*
                     *  easy mala
                     */

                    items(count = 1){

                        Spacer(modifier = Modifier.height(14.dp))

                    }

                    items(count = 1) {

                        Text( text = "সহজ মালা",
                            fontSize = ScreenSize().responsiveTextSize(17, 19, 21),
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, end = 14.dp)
                                .align(Alignment.CenterStart)

                        )

                    }

                    items(count = 1){

                        Spacer(modifier = Modifier.height(2.dp))

                    }

                    items(
                        items = achievementEasyMalaList,
                        key = {it.id}
                    ){

                        AchievementItem(
                            isDark = isDark,
                            isAchievementCompleted = it.isCompleted,
                            achievementCount = it.achievementCount
                        )

                    }//items

                    items(count = 1){

                        Spacer(modifier = Modifier.height(24.dp))

                    }

                    /*
                     *  Boishnob mala
                    */

                    items(count = 1) {
                        Text( text = "বৈষ্ণব মালা",
                            fontSize = ScreenSize().responsiveTextSize(17, 19, 21),
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, end = 14.dp)
                                .align(Alignment.CenterStart)

                        )
                    }

                    items(count = 1){

                        Spacer(modifier = Modifier.height(2.dp))

                    }

                    items(
                        items = achievementBoishnobMalaList,
                        key = {it.id}
                    ){

                        AchievementItem(
                            isDark = isDark,
                            isAchievementCompleted = it.isCompleted,
                            achievementCount = it.achievementCount
                        )

                    }//items

                    items(count = 1){

                        Spacer(modifier = Modifier.height(24.dp))

                    }

                    /*
                    *  shiv mala
                    */

                    items(count = 1) {
                        Text( text = "শিব মালা",
                            fontSize = ScreenSize().responsiveTextSize(17, 19, 21),
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.SemiBold,
                            color = Color(0xFF000000),
                            textAlign = TextAlign.Start,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 14.dp, end = 14.dp)
                                .align(Alignment.CenterStart)

                        )
                    }

                    items(count = 1){

                        Spacer(modifier = Modifier.height(2.dp))

                    }

                    items(
                        items = achievementShivMalaList,
                        key = {it.id}
                    ){

                        AchievementItem(
                            isDark = isDark,
                            isAchievementCompleted = it.isCompleted,
                            achievementCount = it.achievementCount
                        )

                    }//items

                    items(count = 1){

                        Spacer(modifier = Modifier.height(14.dp))

                    }

                }//lazy column

            }//condition

        }//box

    }//scaffold

}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
    isAnyDialogVisible : Boolean = false
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
                    .align(Alignment.Center)

            )

        }

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun AchievementItem(
    isDark: Boolean = false,
    isAchievementCompleted : Boolean = false,
    achievementCount : String = "১০০০"
) {

    val countMap = mapOf(
        "1000" to "১০০০",
        "5000" to "৫০০০",
        "10000" to "১০০০০",
        "50000" to "৫০০০০",
        "100000" to "১০০০০০",
        "500000" to "৫০০০০০"
    )

    Box(

        modifier = Modifier
            .fillMaxWidth()

    ) {

        Row(

            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .align(Alignment.CenterStart)

        ) {
            /* Complete image  */
            if (!isAchievementCompleted){

                Image( painter = painterResource(R.drawable.img_lock),
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(20.dp)
                        .alpha(0.5f)
                        .align(Alignment.CenterVertically)

                )

            }else{

                Image( painter = painterResource(R.drawable.img_achievement),
                    contentDescription = "",
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(20.dp)
                        .align(Alignment.CenterVertically)

                )

            }

            Spacer(modifier = Modifier.width(18.dp))

            Text( text = "${countMap[achievementCount] ?: "০"} বার জপ",
                fontSize = ScreenSize().responsiveTextSize(16, 18, 20),
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.SemiBold,
                color = Color(0xFF525252),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .alpha(if (isAchievementCompleted) 1f else 0.5f)
                    .align(Alignment.CenterVertically)

            )

        }//row

        /* Complete & Incomplete status */

        Box(

            modifier = Modifier
                .padding(7.dp)
                .wrapContentWidth()
                .clip(shape = RoundedCornerShape(40.dp))
                .background(
                    color = if (isAchievementCompleted) Color(0x6392EF96) else Color(
                        0xFFE8E8E8
                    )
                )
                .padding(start = 16.dp, end = 16.dp)
                //.alpha(if (isAchievementCompleted) 1f else 0.5f)
                .align(Alignment.CenterEnd)

        ) {

            Text( text = if (isAchievementCompleted) "সম্পূর্ণ" else "অসম্পূর্ণ",
                fontSize = ScreenSize().responsiveTextSize(12, 14, 16),
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.SemiBold,
                color = if (isAchievementCompleted) Color(0xFF494848) else Color.Black.copy(alpha = 0.6f),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .wrapContentWidth()

            )

        }//box


    }//box

}//fun end
