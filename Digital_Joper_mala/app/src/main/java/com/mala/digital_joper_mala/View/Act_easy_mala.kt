package com.mala.digital_joper_mala.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.DarkToolBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightBackground
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightStatusBar
import com.mala.digital_joper_mala.View.main_theme_ui.theme.LightToolBar
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.CacheHelper_
import com.mala.digital_joper_mala.Helper.SanitizeHelper
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme

class Act_easy_mala : ComponentActivity() {

    private lateinit var cacheHelper: CacheHelper_

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            init()

            var isDark by remember { mutableStateOf(false) }
            var counter by remember { mutableStateOf(0L) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            val cacheData = cacheHelper.getCache("counter_limit", "")

            Digital_Joper_malaTheme{

                EasyMalaFullScreen(
                    isDark = isDark,
                    backClick = { finish() },
                    saveClick = {
                        cacheHelper.deleteCache("counter_limit")
                        cacheHelper.setCache("counter_limit", counter.toString())
                                },
                    counter = { counter = it },
                    readCounter = if (cacheData.isNotEmpty() && cacheData != null) cacheData else ""
                )


            }


        }
    }// on create==============================================

    private fun init(){

        cacheHelper = CacheHelper_(this, "easy_mala")

    }

}//class=======================================================

@Preview(showBackground = true)
@Composable
private fun EasyMalaFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    saveClick: () -> Unit = {},
    counter : (Long) -> Unit = {},
    readCounter : String = ""

) {

    var isInputFiledVisible by remember { mutableStateOf(false) }
    var totalCount by remember { mutableStateOf(0L) }

    if (readCounter.isNotEmpty() && readCounter != "null") totalCount = readCounter.toLong() else totalCount = 0L

    Scaffold(
        topBar = {Toolbar(
            isDark = isDark,
            backClick = backClick,
            editClick = { isInputFiledVisible = true },
            count = totalCount
        )},
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
                .imePadding()

        ) {

            Spacer(modifier = Modifier.height(20.dp))

            Counter(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                counterLimit = totalCount,
                isDark = isDark
            )

            if (isInputFiledVisible){

                CounterLimit(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    saveClick = {
                        saveClick()
                        isInputFiledVisible = false },
                    closeClick = { isInputFiledVisible = false },
                    input = { totalCount = it }
                )

                counter(totalCount)

            }


        }//box


    }//scaffold

}//fun end


@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
    editClick : () -> Unit = {},
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

            modifier = Modifier.wrapContentWidth().align(Alignment.CenterEnd)

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
                onClick = editClick,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = CircleShape)
                    //.background(color = Color.Green)
                    .align(Alignment.CenterVertically)
                    .size(40.dp)
            ) {

                Icon(
                    painter = painterResource(R.drawable.ic_edit2),
                    contentDescription = "Edit",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(20.dp)

                )

            }

        }//row

    }

}//fun end


@Preview(showBackground = true)
@Composable
private fun Counter(
    modifier: Modifier = Modifier,
    counterLimit : Long = 0,
    isDark: Boolean = false
    
) {
    
    var number by remember { mutableStateOf(0L) }

    val maxWidth = 120.dp

    val width = if (number.toString().length > 5){

        maxWidth + ((number.toString().length - 5) * 5).dp

    }else maxWidth




    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp)

    ) {

        Column(

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center)

        ) {

            Box(

                modifier = Modifier
                    .width(width)
                    .height(70.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color =  if (isDark) Color(0xFFB48E8E) else Color(0xFFEADADA))
                    //.padding(40.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                Text(BanglaHelper.readLong(number),
                    fontSize = 25.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)
                        .padding(10.dp)
                )

            }//box

            Spacer(modifier = Modifier.height(130.dp))

            //buttons
            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                Box(

                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .shadow(elevation = 5.dp, shape = CircleShape)
                        .clip(shape = CircleShape)
                        .clickable { number = 0L }
                        .background(color = Color(0xFFF44336))
                        .align(Alignment.CenterStart)

                ) {

                    Icon( painter = painterResource(R.drawable.ic_refresh),
                        contentDescription = "Reset",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(30.dp)
                            .align(Alignment.Center)

                    )

                }

                Box(

                    modifier = Modifier
                        .width(60.dp)
                        .height(60.dp)
                        .shadow(elevation = 5.dp, shape = CircleShape)
                        .clip(shape = CircleShape)
                        .clickable {

                            if (counterLimit > 0L){

                                if (number < counterLimit) number++

                            }else{

                                number++
                            }


                        }
                        .background(color = Color(0xFF4CAF50))
                        .align(Alignment.CenterEnd)

                ) {

                    Icon( painter = painterResource(R.drawable.ic_add),
                        contentDescription = "add",
                        tint = Color(0xFFFFFFFF),
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(30.dp)
                            .align(Alignment.Center)

                    )

                }

            }//box

        }//column

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
private fun CounterLimit(
    modifier: Modifier = Modifier,
    saveClick : () -> Unit = {},
    closeClick : () -> Unit = {},
    input : (Long) -> Unit = {}
) {

    var counterInput by remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }

    Box(
       modifier = modifier
           .fillMaxWidth()
           .padding(7.dp)
        
    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White)
                .padding(12.dp)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(10.dp))
                    .border(width = 1.dp, color = Color(0xFF9A7A7A), shape = RoundedCornerShape(10.dp))
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                if (counterInput.isBlank()){

                    Text("জপের লিমিট",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = Color.Gray,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(3.dp)
                            .align(Alignment.CenterStart)
                    )

                }

                BasicTextField(
                    value = counterInput,
                    onValueChange = {

                        if (it.length <= 12){

                            counterInput = it

                        }},
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF000000)
                    ),
                    keyboardOptions = KeyboardOptions(

                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done

                    ),
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
                            tint = Color(0xFF5E4F4F),
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

            Spacer(modifier = Modifier.height(16.dp))

            Row(

                modifier = Modifier.fillMaxWidth()

            ) {

                Box(
                    modifier = Modifier
                        .weight(0.5f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(15.dp))
                        .clickable{ closeClick() }
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
                        ){

                            input(SanitizeHelper.sanitizeNumber(counterInput))

                            Log.d("input", SanitizeHelper.sanitizeNumber(counterInput).toString())

                            saveClick()

                        }
                        .alpha(if (counterInput.isEmpty()) 0.5f else 1f)
                        .background(color = Color(0xFF4CAF50))
                        .padding(5.dp)
                        .align(Alignment.CenterVertically)

                ) {

                    Text("সেভ",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
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

