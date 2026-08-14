package com.mala.digital_joper_mala.Helper

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
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

class ComposeHelper {

    @Preview(showBackground = true)
    @Composable
    fun Counter(
        modifier: Modifier = Modifier,
        counterLimit : Long = 0,
        currentCount : (Long) -> Unit = {},
        isDark: Boolean = false,
        isVibrationEnabled : Boolean = true
    ) {

        var number by remember { mutableStateOf(0L) }

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
                        .background(color =  if (isDark) Color(0xFF9F8282) else Color(0xFFEADADA))
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