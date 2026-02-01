package com.mala.digital_joper_mala.Activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity

import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.Activity.ui.theme.DarkBackground
import com.mala.digital_joper_mala.Activity.ui.theme.DarkChartBackground
import com.mala.digital_joper_mala.Activity.ui.theme.DarkCounterText
import com.mala.digital_joper_mala.Activity.ui.theme.DarkMonthText
import com.mala.digital_joper_mala.Activity.ui.theme.DarkStatusBar
import com.mala.digital_joper_mala.Activity.ui.theme.DarkToolBar
import com.mala.digital_joper_mala.Activity.ui.theme.Digital_Joper_malaTheme
import com.mala.digital_joper_mala.Activity.ui.theme.LightBackground
import com.mala.digital_joper_mala.Activity.ui.theme.LightChartBackground
import com.mala.digital_joper_mala.Activity.ui.theme.LightCounterText
import com.mala.digital_joper_mala.Activity.ui.theme.LightMonthText
import com.mala.digital_joper_mala.Activity.ui.theme.LightStatusBar
import com.mala.digital_joper_mala.Activity.ui.theme.LightToolBar
import com.mala.digital_joper_mala.Database.JopaChartDB
import com.mala.digital_joper_mala.Model.JopaChartModel
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.Utils.BanglaHelper
import com.mala.digital_joper_mala.Utils.ThemeHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

class Act_chart : ComponentActivity() {

    //DB
    private lateinit var jopaChartDB : JopaChartDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val chartList = remember { mutableStateListOf<JopaChartModel>() }
            var isDelete by remember { mutableStateOf(false) }
            var isDark by remember { mutableStateOf(false) }


            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            init()

            LaunchedEffect(Unit) {

                val item = withContext(Dispatchers.IO){

                    jopaChartDB.getAll()
                    
                }
                Log.d("list", item.toString())

                chartList.clear()
                chartList.addAll(item)

            }

            if (isDelete){

                LaunchedEffect(Unit) {

                    withContext(Dispatchers.IO){

                        jopaChartDB.deleteAll()

                    }

                    chartList.clear()

                }

            }


            Digital_Joper_malaTheme {

                FullScreen(
                    backClick = {finish()},
                    deleteClick = { isDelete = true },
                    list = chartList,
                    isDark = isDark
                )

            }
        }

    }// on create=============================================

    private fun init(){

        jopaChartDB = JopaChartDB(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        jopaChartDB.closeDB()
    }


}//class======================================================


@Preview(showBackground = true)
@Composable
private fun FullScreen(
    backClick: () -> Unit = {},
    deleteClick: () -> Unit = {},
    list: List<JopaChartModel> = emptyList(),
    isDark : Boolean = false
    ) {

    val context = LocalContext.current
    val bgColor = colorResource(R.color.background_color)
    var isDialogVisible by remember { mutableStateOf(false) }

    val monthList = arrayOf("জানুয়ারি", "ফেব্রুয়ারি", "মার্চ", "এপ্রিল", "মে", "জুন", "জুলাই", "আগস্ট", "সেপ্টেম্বর", "অক্টোবর", "নভেম্বর", "ডিসেম্বর")


    val jan = list.firstOrNull()?.january ?: "০"
    val feb = list.firstOrNull()?.february ?: "০"
    val mar = list.firstOrNull()?.march ?: "০"
    val apr = list.firstOrNull()?.april ?: "০"
    val may = list.firstOrNull()?.may ?: "০"
    val jun = list.firstOrNull()?.june ?: "০"
    val jul = list.firstOrNull()?.july ?: "০"
    val aug = list.firstOrNull()?.august ?: "০"
    val sep = list.firstOrNull()?.september ?: "০"
    val oct = list.firstOrNull()?.october ?: "০"
    val nov = list.firstOrNull()?.november ?: "০"
    val dec = list.firstOrNull()?.december ?: "০"


    Scaffold(
        topBar = {
            Toolbar( backClick = {backClick()},
                deleteClick = {deleteClick()},
                infoClick = {isDialogVisible = true},
                isDark = isDark
            )},
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isDark) DarkBackground else LightBackground),

        )
    { innerPadding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = bgColor)
                    .verticalScroll(rememberScrollState())
                    .align(Alignment.TopCenter)

            ) {

                CounterChart(
                    nMonth = monthList[0],
                    count = jan.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[1],
                    count = feb.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[2],
                    count = mar.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[3],
                    count = apr.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[4],
                    count = may.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[5],
                    count = jun.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[6],
                    count = jul.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[7],
                    count = aug.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[8],
                    count = sep.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[9],
                    count = oct.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[10],
                    count = nov.toString(),
                    isDark = isDark
                )

                CounterChart(
                    nMonth = monthList[11],
                    count = dec.toString(),
                    isDark = isDark
                )

            }//column

            if (isDialogVisible){

                UserDialog(
                    closeClick = {isDialogVisible = false},
                    modifier = Modifier
                        .align(Alignment.Center),
                    isDark = isDark
                )

            }

        }//box

    }//scaffold

}//fun end


@Preview(showBackground = true)
@Composable
private fun Toolbar(
    backClick : () -> Unit = {},
    deleteClick : () -> Unit = {},
    infoClick : () -> Unit = {},
    isDark : Boolean = false
) {

    //val bgColor = colorResource(R.color.toolbar_color)
    //val isDark = isSystemInDarkTheme()

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

        Row(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd)
        ) {

            IconButton(
                onClick = infoClick,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = CircleShape)
                    //.background(color = Color.Green)
                    .align(Alignment.CenterVertically)
                    .size(37.dp)
            ) {

                Icon( painter = painterResource(R.drawable.ic_alert_info),
                    contentDescription = "Back",
                    tint = Color(0xFFFFFFFF),
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(23.dp)

                )

            }

            Spacer(modifier = Modifier.width(10.dp))

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

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
private fun CounterChart(nMonth : String = "মাস", count : String = "১৯২০", isDark : Boolean = false) {

    

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(10.dp))
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = if(isDark) DarkChartBackground else LightChartBackground)
                .padding(7.dp)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {
                
                Text(nMonth,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) DarkMonthText else LightMonthText,
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterStart)
                    )

                Text(buildAnnotatedString{

                    withStyle(style = SpanStyle(

                        color = if (isDark) DarkCounterText else LightCounterText,
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = if (count.toInt() >= 1920) FontWeight.Bold else FontWeight.Normal

                    )){append(BanglaHelper.readInt(count.toInt()))}

                    withStyle(style = SpanStyle(

                        color = if (isDark) DarkCounterText else LightCounterText,
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont()

                    )){append(" /")}

                    withStyle(style = SpanStyle(

                        color = if (isDark) DarkCounterText else LightCounterText,
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont()

                    )){append("১৯২০")}

                },
                    style = TextStyle(platformStyle = PlatformTextStyle(includeFontPadding = false)),
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterEnd)
                    )

            }//box

            VerticalProgressBar(
                progressValue = count.toInt().toFloat(),
                maxValue = 1920f,
                barHeight = 10
            )

        }//column
        

    }//box

}//fun end


@Preview(showBackground = true)
@Composable
private fun VerticalProgressBar(
    progressValue : Float = 0.5f,
    maxValue : Float = 0f,
    barHeight : Int = 5,
    isDark : Boolean = false

) {

    val progress = (progressValue / maxValue).coerceIn(0f, 1f)

    Box(  //track

        modifier = Modifier
            .fillMaxWidth()
            .height(barHeight.dp)
            .clip(shape = RoundedCornerShape(15.dp))
            .background(color = Color(0xFFEAE6E6))


    ) {

        Box(  //progress
            modifier = Modifier
                .fillMaxWidth(fraction = progress)
                .height(barHeight.dp)
                .clip(shape = RoundedCornerShape(15.dp))
                .background(color = Color(0xFF673AB7))
        )

    }
    
}//fun end


@Preview(showBackground = true)
@Composable
private fun UserDialog (
    closeClick : () -> Unit = {},
    modifier: Modifier = Modifier,
    isDark : Boolean = false

) {

    val font = FontFamily(Font(R.font.noto_serif_bengali))

    Box(

        modifier = modifier.fillMaxWidth().padding(7.dp)

    ) {
        
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 10.dp, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .background(color = Color(0xFFFFFFFF))
                .padding(12.dp)

        ) {

            Text("প্রতিদিন মাত্র ৬৪ বার জপ করুন, আর এই মাসে ১৯২০ জপের লক্ষ্য সহজেই পূর্ণ করুন।\n" +
                    "প্রতিটি জপ আপনার মন, শক্তি ও আত্মাকে আরও সমৃদ্ধ করবে।\n" +
                    "আজ থেকেই শুরু করুন, নিজের অদম্য শক্তি এবং শান্তি অনুভব করুন!",
                fontSize = 15.sp,
                fontFamily = font,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF000000),
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(5.dp)
                    .align(Alignment.Start)
                )

            Text("বন্ধ",
                fontSize = 13.sp,
                fontFamily = font,
                fontWeight = FontWeight.Normal,
                color = Color(0xFF655252),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable{ closeClick() }
                    //.background(color = Color.Gray)
                    .padding(start = 14.dp, end = 14.dp, top = 5.dp, bottom = 5.dp)
                    .align(Alignment.End)
                )

        }//column

    }//box

}//fun end

