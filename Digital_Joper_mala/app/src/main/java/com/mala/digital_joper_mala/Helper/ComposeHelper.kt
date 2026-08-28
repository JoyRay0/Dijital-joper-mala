package com.mala.digital_joper_mala.Helper

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.R
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ComposeHelper {

    @Preview(showBackground = true)
    @Composable
    fun Counter(
        modifier: Modifier = Modifier,
        counterLimit : Long = 0,
        currentCount : (Long) -> Unit = {},
        isDark: Boolean = false,
        isVibrationEnabled : Boolean = true,
        countNumber : String = ""
    ) {

        var number by remember { mutableStateOf(0L) }

        LaunchedEffect(countNumber) {

            if (countNumber.isEmpty()){

                number = 0L

            }else{

                number = countNumber.toLong()

            }

        }

        val context = LocalContext.current

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
                        .background(color = if (isDark) Color(0xFF9F8282) else Color(0xFFEADADA))
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

                    if (number > 0L) currentCount(number)

                }//box

                Spacer(modifier = Modifier.height(130.dp))

                //buttons
                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .align(Alignment.CenterHorizontally)

                ) {

                    /* Reset button */

                    Box(

                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .shadow(elevation = 5.dp, shape = CircleShape)
                            .clip(shape = CircleShape)
                            .clickable {
                                number = 0L
                                if (isVibrationEnabled) vibration(context)
                                currentCount(0L)
                            }
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

                    /* Add button */

                    Box(

                        modifier = Modifier
                            .width(60.dp)
                            .height(60.dp)
                            .shadow(elevation = 5.dp, shape = CircleShape)
                            .clip(shape = CircleShape)
                            .clickable {

                                if (isVibrationEnabled) vibration(context)

                                /*
                                if (counterLimit > 0L){

                                    if (number < counterLimit) number++

                                }else{

                                    number++
                                }

                                 */

                                if (counterLimit > 0L) {

                                    if (counterLimit != number && counterLimit > number) number++

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
    fun MilestonesDialog(
        modifier: Modifier = Modifier,
        closeClick: () -> Unit = {},
        currentCount : Long = 1000L,
        isDark: Boolean = false
    ) {

        val countMilestones = listOf(1000L, 5000L, 10000L, 50000L, 100000L, 500000L)
        val milestonesTitle = listOf(
            "\uD83C\uDF89 অভিনন্দন!",
            "\uD83C\uDF38 অসাধারণ সাধনা!",
            "\uD83C\uDF1F ১০,০০০ জপ সম্পন্ন!",
            "\uD83C\uDFC6 অসাধারণ অর্জন!",
            "\uD83C\uDF3F সাধনার নতুন অধ্যায়",
            "\uD83E\uDEB7 অসীম নিষ্ঠার স্বাক্ষর"
        )
        val milestonesDescription = listOf(
            "আপনি সফলভাবে ১,০০০ বার জপ সম্পন্ন করেছেন। আপনার এই সাধনা অব্যাহত থাকুক। \uD83D\uDE4F",
            "আপনার জপের সংখ্যা ৫,০০০ পূর্ণ হয়েছে। নিয়মিত জপের এই সুন্দর অভ্যাস ধরে রাখুন। \uD83D\uDE4F",
            "আপনার অধ্যবসায় সত্যিই প্রশংসনীয়। ১০,০০০ জপের এই অর্জনের জন্য অভিনন্দন। \uD83D\uDE4F",
            "আপনি ৫০,০০০ জপ সম্পন্ন করেছেন! আপনার নিষ্ঠা ও সাধনার এই পথ আরও সুন্দর হোক। \uD83D\uDE4F",
            "এক লাখ জপের এই সুন্দর মাইলফলক আপনার নিষ্ঠার পরিচয়। আপনার সাধনা আরও গভীর ও সুন্দর হোক।",
            "পাঁচ লাখ জপ সম্পন্ন করা সত্যিই এক অসাধারণ অর্জন। আপনার এই নিরবচ্ছিন্ন সাধনার পথ আরও আলোকিত হোক। \uD83D\uDE4F"
        )

        Box(

            modifier = modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = null
                ) {}
                .padding(12.dp)

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    //.shadow(elevation = 3.dp, shape = RoundedCornerShape(14.dp))
                    .clip(shape = RoundedCornerShape(22.dp))
                    .clickable(
                        indication = null,
                        interactionSource = null
                    ) {}
                    .background(color = if (isDark) Color.DarkGray else Color.White)
                    .padding(7.dp)
                    .align(Alignment.BottomCenter)

            ) {

                Spacer(modifier = Modifier.height(7.dp))

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally)

                ) {

                    Text(
                        text = "\uD83C\uDF89 নতুন অর্জন!",
                        fontSize = 18.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color(0xFFEAEAEA) else Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.Center)

                    )

                    /* close button */
                    Box(

                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            //.background(color = Color.Gray)
                            .clickable { closeClick() }
                            .size(30.dp)
                            .align(Alignment.CenterEnd)

                    ) {

                        Icon( painter = painterResource(com.mala.digital_joper_mala.R.drawable.ic_wrong),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(18.dp)
                                .align(Alignment.Center)

                        )

                    }

                }//box

                Spacer(modifier = Modifier.height(15.dp))

                Text(
                    text = when(currentCount){

                        countMilestones[0] -> milestonesTitle[0]
                        countMilestones[1] -> milestonesTitle[1]
                        countMilestones[2] -> milestonesTitle[2]
                        countMilestones[3] -> milestonesTitle[3]
                        countMilestones[4] -> milestonesTitle[4]
                        countMilestones[5] -> milestonesTitle[5]
                        else -> ""

                    },
                    fontSize = 16.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)

                )

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    text = when(currentCount){

                        countMilestones[0] -> milestonesDescription[0]
                        countMilestones[1] -> milestonesDescription[1]
                        countMilestones[2] -> milestonesDescription[2]
                        countMilestones[3] -> milestonesDescription[3]
                        countMilestones[4] -> milestonesDescription[4]
                        countMilestones[5] -> milestonesDescription[5]
                        else -> ""

                    },
                    fontSize = 16.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color(0xFFE3E2E2) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally)

                )

                Spacer(modifier = Modifier.height(12.dp))

            }//column

        }//box

    }//fun end


    @Composable
    fun Dialog(
        //modifier: Modifier = Modifier,
        headerText : String = "Header",
        headerTestSize : Float = 18f,
        headerTextWeight : FontWeight = FontWeight.Bold,
        isHeaderEnabled : Boolean = true,
        isDark: Boolean = false,
        closeClick: () -> Unit = {},
        onDismissClick : () -> Unit = {},
        content : @Composable (ColumnScope.() -> Unit)
    ) {

        Box(

            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    indication = null,
                    interactionSource = null
                ) { onDismissClick() }
                .background(
                    color = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(
                        alpha = 0.5f
                    )
                )
                .padding(12.dp)

        ) {

            Column(

                modifier = Modifier
                    .fillMaxWidth()
                    //.shadow(elevation = 3.dp, shape = RoundedCornerShape(14.dp))
                    .clip(shape = RoundedCornerShape(22.dp))
                    .clickable(
                        indication = null,
                        interactionSource = null
                    ) {}
                    .background(color = if (isDark) Color.DarkGray else Color.White)
                    .padding(7.dp)
                    .align(Alignment.BottomCenter)

            ) {

                Spacer(modifier = Modifier.height(7.dp))

                if (isHeaderEnabled){

                    Box(

                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)

                    ) {

                        Text(
                            text = headerText,
                            fontSize = headerTestSize.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = headerTextWeight,
                            color = if (isDark) Color(0xFFEAEAEA) else Color(0xFF000000),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .wrapContentWidth()
                                .align(Alignment.Center)

                        )

                        /* close button */

                        Box(

                            modifier = Modifier
                                .wrapContentWidth()
                                .clip(shape = CircleShape)
                                //.background(color = Color.Gray)
                                .clickable { closeClick() }
                                .size(30.dp)
                                .align(Alignment.CenterEnd)

                        ) {

                            Icon( painter = painterResource(com.mala.digital_joper_mala.R.drawable.ic_wrong),
                                contentDescription = "",
                                tint = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                                modifier = Modifier
                                    .wrapContentWidth()
                                    .size(18.dp)
                                    .align(Alignment.Center)

                            )

                        }// icon box

                    }//box

                }//condition

                content()

            }//column

        }//box

    }//fun end


    @Preview(showBackground = true)
    @Composable
    fun MantraItem(
        boxModifier : Modifier = Modifier,
        columnModifier : Modifier = Modifier,
        isDark: Boolean = false,
        title : String = "Test",
        mantra : String = "Test"
    ) {

        Box(

            modifier = boxModifier
                .fillMaxWidth()
                .padding(5.dp)

        ) {

            Column(

                modifier = columnModifier
                    .fillMaxWidth()
                    .padding(7.dp)

            ) {

                Text( text = title,
                    fontSize = 16.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color(0xFFE0DFDF) else Color(0xFF313030),
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

    fun getDate(banglaDayDate : (bDay : String, bDate : String) -> Unit){

        val currentDay = SimpleDateFormat("EEEE", Locale.getDefault()).format(Date())
        val currentDate = SimpleDateFormat("dd", Locale.getDefault()).format(Date())
        val currentMonth = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
        val currentYear = SimpleDateFormat("yyyy", Locale.getDefault()).format(Date())

        val banglaDay = mutableMapOf<String, String>()

        banglaDay["Monday"] = "সোমবার"
        banglaDay["Tuesday"] = "মঙ্গলবার"
        banglaDay["Wednesday"] = "বুধবার"
        banglaDay["Thursday"] = "বৃহস্পতিবার"
        banglaDay["Friday"] = "শুক্রবার"
        banglaDay["Saturday"] = "শনিবার"
        banglaDay["Sunday"] = "রবিবার"

        val banglaDate = BanglaHelper.readInt(currentDate.toIntOrNull() ?: 0)
        val banglaMonth = BanglaHelper.readInt(currentMonth.toIntOrNull() ?: 0)
        val banglaYear = BanglaHelper.readInt(currentYear.toIntOrNull() ?: 0)

        if (!banglaDay[currentDay].isNullOrEmpty() && banglaDate.isNotEmpty() && banglaMonth.isNotEmpty() && banglaYear.isNotEmpty()){

            banglaDayDate("${banglaDay[currentDay]}", "$banglaDate - $banglaMonth - $banglaYear")

        }

    }

    private fun vibration(context : Context){

        val vibrator =
            context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    50,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )

        } else {

            @Suppress("DEPRECATION")
            vibrator.vibrate(50)
        }

    }

}