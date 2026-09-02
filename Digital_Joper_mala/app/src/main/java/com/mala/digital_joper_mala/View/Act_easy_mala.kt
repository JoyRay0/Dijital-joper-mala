package com.mala.digital_joper_mala.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkToolBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightToolBar
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.ComposeHelper
import com.mala.digital_joper_mala.Helper.IntentHelper
import com.mala.digital_joper_mala.Helper.SanitizeHelper
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.Helper.VibrationHelper
import com.mala.digital_joper_mala.Model.Achievement
import com.mala.digital_joper_mala.Model.EasyMalaItem
import com.mala.digital_joper_mala.Presenter.AchievementPresenter
import com.mala.digital_joper_mala.Presenter.Achievements
import com.mala.digital_joper_mala.Presenter.EasyMala
import com.mala.digital_joper_mala.Presenter.EasyMalaPresenter
import com.mala.digital_joper_mala.Presenter.JopHistoryPresenter
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme

class Act_easy_mala : ComponentActivity(), EasyMala, Achievements {
    private lateinit var tracker : TrackScreen
    private lateinit var presenter : EasyMalaPresenter
    private lateinit var achievementPresenter : AchievementPresenter
    private lateinit var historyPresenter : JopHistoryPresenter

    //init============
    private val achievementList = mutableStateListOf<Achievement>()
    private val favoriteMantraList = mutableStateListOf<EasyMalaItem>()
    private val userMantraList = mutableStateListOf<EasyMalaItem>()
    private val lastCountCache = mutableStateOf("")
    private val currentCount = mutableStateOf("")
    private val getCountLimit = mutableStateOf("")
    private val pCount = mutableLongStateOf(0L)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            var isDark by remember { mutableStateOf(false) }
            var isVibration by remember { mutableStateOf(false) }
            var reloadAchievementListCount by remember { mutableStateOf(0) }
            var reloadCountLimit by remember { mutableStateOf(0) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            if (VibrationHelper.IsVibration(this)) isVibration = true else isVibration = false

            presenter.getLastCountCache()
            presenter.getAllFavoriteMantra()
            presenter.getAllUserMantra()

            LaunchedEffect(
                reloadAchievementListCount,
                reloadCountLimit
            ) {

                achievementPresenter.getAchievement("easy_mala")

                presenter.getCountLimit()

            }
            reloadCountLimit++

            Digital_Joper_malaTheme{

                EasyMalaFullScreen(
                    isDark = isDark,
                    backClick = {
                        IntentHelper.normalIntent(this, Act_home::class.java)
                        finish()
                                },
                    isVibration = isVibration,
                    saveAchievementCount = { count ->

                        achievementPresenter.insertAchievement("easy_mala", count.toString(), isInserted = { success ->

                            if (success) reloadAchievementListCount++

                        })

                    },
                    achievementList = achievementList,
                    currentCount = {
                        currentCount.value = it.toString()

                    },
                    lastCountCache = lastCountCache.value,
                    setCountLimit = {
                        presenter.setCountLimit(it.ifEmpty { "" })
                        reloadCountLimit++
                    },
                    getCountLimit = getCountLimit.value,
                    favoriteMantraList = favoriteMantraList,
                    userMantraList = userMantraList,
                    pCount = { pCount.longValue = it }
                )


            }

            BackHandler() {

                IntentHelper.normalIntent(this, Act_home::class.java)
                finish()

            }


        }
    }// on create==============================================

    private fun init(){

        tracker = TrackScreen(this)

        presenter = EasyMalaPresenter(this, this)

        achievementPresenter = AchievementPresenter(this, this)

        historyPresenter = JopHistoryPresenter(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_easy_mala)

        presenter.setLastCountCache(currentCount.value)

        historyPresenter.insertJopCount(pCount.longValue)

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
        achievementPresenter.onDestroy()
    }

    override fun favoriteMantraList(list: List<EasyMalaItem>) {
        favoriteMantraList.clear()
        favoriteMantraList.addAll(list)
    }

    override fun userMantraList(list: List<EasyMalaItem>) {
        userMantraList.clear()
        userMantraList.addAll(list)
    }

    override fun lastCountCache(value: String) {
        lastCountCache.value = value
    }

    override fun countLimit(limit: String) {
        getCountLimit.value = limit
    }

    override fun achievementCountList(list: List<Achievement>) {
        achievementList.clear()
        achievementList.addAll(list)
    }

}//class=======================================================

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun EasyMalaFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    isVibration : Boolean = false,
    saveAchievementCount : (Long) -> Unit = {},
    achievementList : List<Achievement> = emptyList(),
    currentCount : (Long) -> Unit = {},
    lastCountCache : String = "",
    setCountLimit: (String) -> Unit = {},
    getCountLimit: String = "0",
    favoriteMantraList : List<EasyMalaItem> = emptyList(),
    userMantraList : List<EasyMalaItem> = emptyList(),
    pCount : (Long) -> Unit = {}
    ) {

    var isMantraDialogVisible by remember { mutableStateOf(false) }
    var isCounterEditVisible by remember { mutableStateOf(false) }
    var isAchievementDialogVisible by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0L) }
    var isAnyDialogVisible by remember { mutableStateOf(false) }

    val countList = listOf(1000L, 5000L, 10000L, 50000L, 100000L, 500000L)

    LaunchedEffect(count, achievementList) {

        /* current count to save in cache */

        currentCount(count)

        /* check for achievement dialog */
        val isExists = achievementList.any {

            it.achievementCount == count.toString()

        }

        if (!isExists && count in countList) isAchievementDialogVisible = true

    }

    LaunchedEffect(
        isMantraDialogVisible,
        isCounterEditVisible,
        isAchievementDialogVisible
    ) {

        if (isMantraDialogVisible || isCounterEditVisible || isAchievementDialogVisible){

            isAnyDialogVisible = true

        }else{

            isAnyDialogVisible = false

        }

    }

    Scaffold(
        topBar = {Toolbar(
            isDark = isDark,
            backClick = backClick,
            countEdit = {
                isCounterEditVisible = true
            },
            isAnyDialogVisible = isAnyDialogVisible,
            count = getCountLimit.toLongOrNull() ?: 0L
        )},

        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isDark) DarkStatusBar else LightStatusBar)
            .systemBarsPadding()
    ) { innerPadding ->

        Column(

            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDark) DarkBackground else LightBackground)
                .padding(innerPadding)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)

            ) {

                /* counter */
                ComposeHelper().Counter(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    counterLimit = getCountLimit.toLongOrNull() ?: 1000L,
                    currentCount = { count = it },
                    isDark = isDark,
                    isVibrationEnabled = isVibration,
                    countNumber = lastCountCache,
                    pCount = { pCount(it) }
                )

                /* Achievements */
                if (isAchievementDialogVisible){

                    ComposeHelper().MilestonesDialog(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(
                                    alpha = 0.5f
                                )
                            ),
                        closeClick = {
                            isAchievementDialogVisible = false
                            saveAchievementCount(count)
                        },
                        currentCount = count,
                        isDark = isDark
                    )

                }

                /* count edit */

                if (isCounterEditVisible){

                    ModalBottomSheet(
                        onDismissRequest = {isCounterEditVisible = false},
                        modifier = Modifier
                            .fillMaxWidth()
                            .imePadding(),
                        containerColor = if (isDark) Color(0xFF644646) else Color(0xFFFFFFFF),
                        dragHandle = null,

                        ) {

                        CounterEdit(
                            saveClick = {
                                setCountLimit( if (it <= 0) "" else it.toString() )
                                isCounterEditVisible = false },
                            closeClick = { isCounterEditVisible = false },
                            isDark = isDark
                        )

                    }//dialog

                    //counter(totalCount)

                }

                /*  floating mantra button */
                FloatingButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomEnd),
                    isDark = isDark,
                    onClick = { isMantraDialogVisible = true }
                )

                if (isMantraDialogVisible){

                    AllMantra(
                        isDark = isDark,
                        favoriteMantraList = favoriteMantraList,
                        userMantaList = userMantraList,
                        closeClick = { isMantraDialogVisible = false }
                    )

                }

            }//box

        }//column

    }//scaffold

}//fun end


@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
    countEdit : () -> Unit = {},
    isAnyDialogVisible : Boolean = false,
    count : Long = 0
) {

    var isCount by remember { mutableStateOf(false) }

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

        Row(

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd)

        ) {

            if (count > 0L) isCount = true else isCount = false

            if (isCount){

                Text(text = BanglaHelper.readLong(count),
                    fontSize = 20.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)

                )

            }

            Spacer(modifier = Modifier.width(10.dp))

            IconButton(
                onClick = {

                    if (!isAnyDialogVisible){

                        countEdit()

                    }

                },
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = CircleShape)
                    //.background(color = Color.Green)
                    .align(Alignment.CenterVertically)
                    .size(37.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_edit),
                    contentDescription = "edit",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(22.dp)
                        .align(Alignment.CenterVertically)

                )

            }

        }//row

    }

}//fun end


@Preview(showBackground = true)
@Composable
private fun CounterEdit(
    saveClick : (Long) -> Unit = {},
    closeClick : () -> Unit = {},
    isDark: Boolean = false
) {

    var counterInput by remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }

    Box(
       modifier = Modifier
           .fillMaxWidth()
           //.background(color = if (isDark) Color(0xFF644646) else Color(0xFFFFFFFF))
           .padding(7.dp)
        
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(
                        width = 1.dp,
                        color = if (isDark) Color(0xFFE3E0E0) else Color(0xFF9A7A7A),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                if (counterInput.isBlank()){

                    Text("জপের লিমিট",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = if (isDark) Color.LightGray else Color.Gray,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(3.dp)
                            .align(Alignment.CenterStart)
                    )

                }

                BasicTextField(
                    value = if (counterInput.isNotEmpty()) BanglaHelper.readLong(counterInput.toLong()) else counterInput,
                    onValueChange = {

                        if (it.length <= 12){

                            counterInput = it

                        }},
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000)
                    ),
                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done

                    ),
                    cursorBrush = if (isDark) SolidColor(Color.White) else SolidColor(Color.Black),
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(3.dp)
                        .focusRequester(focus)
                        .align(Alignment.CenterStart)

                )

                if (counterInput.isNotEmpty()){

                    IconButton(
                        onClick = { counterInput = "" },
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            //.background(color = Color.LightGray)
                            .size(27.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_clear),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFE7E5E5) else Color(0xFF5E4F4F),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(19.dp)
                                .align(Alignment.Center)

                        )

                    }

                }

                LaunchedEffect(Unit) {

                    focus.requestFocus()

                }

            }//box

            Spacer(modifier = Modifier.height(20.dp))

            Row(

                modifier = Modifier.fillMaxWidth()

            ) {

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(15.dp))
                        .clickable { closeClick() }
                        .background(color = Color(0xFFDEE0DE))
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)

                ) {

                    Text("বন্ধ",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = Color.Black,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Center)
                            .padding(3.dp)

                    )

                }

                Spacer(modifier = Modifier.width(12.dp))

                Box(

                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(15.dp))
                        .clickable(
                            enabled = if (counterInput.isEmpty()) false else true
                        ) {

                            saveClick(SanitizeHelper.sanitizeNumber(counterInput))

                        }
                        .alpha(if (counterInput.isEmpty()) 0.5f else 1f)
                        .background(color = Color(0xFF4CAF50))
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)

                ) {

                    Text("সেভ",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Center)
                            .padding(3.dp)

                        )

                }

            }//row

        }//column


    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun FloatingButton(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    onClick : () -> Unit = {}
) {

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(25.dp)

    ) {

        Box(
            modifier = Modifier
                .wrapContentWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(16.dp),
                    ambientColor = if (isDark) Color.White else Color.Black,
                    spotColor = if (isDark) Color.White else Color.Black
                )
                .clip(shape = RoundedCornerShape(16.dp))
                .clickable { onClick() }
                .background(color = if (isDark) Color(0xFF4B4A4A) else Color(0xFF2196F3))
                .padding(14.dp)
                .align(Alignment.BottomEnd)

        ) {

            Text( text = "মন্ত্র",
                fontSize = 15.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.Center)

            )

        }//box

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
fun AllMantra(
    isDark: Boolean = false,
    favoriteMantraList : List<EasyMalaItem> = emptyList(),
    userMantaList : List<EasyMalaItem> = emptyList(),
    closeClick: () -> Unit = {}
) {

    var selectedIndex by remember { mutableStateOf(0) }

    val currentList = if (selectedIndex == 0) favoriteMantraList else userMantaList

    Box(

        modifier = Modifier
            .fillMaxSize()

    ) {

        ComposeHelper().Dialog(
            headerText = "মন্ত্র সমূহ",
            headerTestSize = 18f,
            isDark = isDark,
            closeClick = { closeClick() }
        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .height(500.dp)

            ) {

                Spacer(modifier = Modifier.height(7.dp))

                /* list selector */

                Row(

                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(12.dp))
                        .background(color = if (isDark) Color(0xFF605F5F) else Color(0xFFF1F1F1))
                        .padding(5.dp)

                ) {

                    val selectorText = listOf("পছন্দের মন্ত্র", "আমার মন্ত্র")

                    selectorText.forEachIndexed { index, text ->

                        Box(

                            modifier = Modifier
                                .weight(1f)
                                .then(
                                    if (selectedIndex == index){

                                        Modifier
                                            .shadow(
                                                elevation = 2.dp,
                                                shape = RoundedCornerShape(12.dp),
                                                ambientColor = if (isDark) Color.White else Color.Black,
                                                spotColor = if (isDark) Color.White else Color.Black,
                                                )

                                    } else Modifier.shadow(elevation = 0.dp)
                                )
                                .clip(shape = RoundedCornerShape(12.dp))
                                .clickable(
                                    indication = null,
                                    interactionSource = null
                                ){selectedIndex = index}
                                .background(color = if (selectedIndex == index){

                                    if (isDark) Color(0xFF2D2D2D) else Color.White

                                } else{

                                    Color.Transparent

                                })
                                .padding(5.dp)

                        ) {

                            Text( text = text,
                                fontSize = 15.sp,
                                fontFamily = BanglaHelper.banglaFont(),
                                fontWeight = FontWeight.Normal,
                                color = if (selectedIndex == index){

                                    if (isDark) Color.White else Color.Black

                                } else{

                                     if (isDark) Color.White.copy(alpha = 0.7f) else Color.Black.copy(alpha = 0.6f)
                                },
                                textAlign = TextAlign.Center,
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .align(Alignment.Center)

                            )

                        }//box

                    }//loop

                }//row list selector

                /* list */

                if (currentList.isEmpty()){

                    Row(

                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.CenterHorizontally),

                    ) {

                        Text( text =
                            if (selectedIndex == 0){
                                "কোনো পছন্দের মন্ত্র নেই। মন্ত্র ভান্ডার থেকে আপনার পছন্দের মন্ত্রটি নির্বাচন করে আবার চেষ্টা করুন।"
                            } else {
                                "এখনও কোনো মন্ত্র সেভ করা হয়নি। প্রথমে একটি মন্ত্র সেভ করুন, তারপর আবার চেষ্টা করুন।"
                            },
                            fontSize = 16.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color.White.copy(alpha = 0.85f) else Color.Black.copy(alpha = 0.85f),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.CenterVertically)

                        )

                    }//row

                }else{

                    LazyColumn(

                        modifier = Modifier
                            .fillMaxWidth()

                    ) {

                        items(

                            items = if (selectedIndex == 0) favoriteMantraList else userMantaList,
                            key = null

                        ){it ->

                            Item(
                                isDark = isDark,
                                title = it.title,
                                mantra = it.mantra
                            )

                        }

                    }//lazy column


                }//condition

            }//column

        }//helper

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun Item(
    isDark: Boolean = false,
    title : String = "Test",
    mantra : String = "Test"
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)

        ) {

            Text( text = title,
                fontSize = 16.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFEFEEEE) else Color(0xFF2D2D2D),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            )

            Spacer(modifier = Modifier.height(12.dp))

            Text( text = mantra,
                fontSize = 16.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            )

        }//column

    }//box

}//fun end

