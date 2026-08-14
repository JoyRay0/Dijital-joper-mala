package com.mala.digital_joper_mala.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mala.digital_joper_mala.Helper.ACTIVITY
import com.mala.digital_joper_mala.Helper.BanglaHelper
import com.mala.digital_joper_mala.Helper.ComposeHelper
import com.mala.digital_joper_mala.Helper.ThemeHelper
import com.mala.digital_joper_mala.Helper.TrackScreen
import com.mala.digital_joper_mala.Helper.VibrationHelper
import com.mala.digital_joper_mala.Model.BoishnobItem
import com.mala.digital_joper_mala.Presenter.BoishnobMala
import com.mala.digital_joper_mala.Presenter.BoishnobMalaPresenter
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*

class Act_boishnob_mala : ComponentActivity(), BoishnobMala {

    private lateinit var tracker : TrackScreen
    private lateinit var presenter : BoishnobMalaPresenter

    //init

    private val mantraList = mutableStateListOf<BoishnobItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            var isDark by remember { mutableStateOf(false) }
            var isVibration by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            if (VibrationHelper.IsVibration(this)) isVibration = true else isVibration = false

            Digital_Joper_malaTheme {

                BoisnobMalaFullScreen(
                    isDark = isDark,
                    backClick = { finish() },
                    mantraList = mantraList,
                    floatingButtonClick = { presenter.getBoishnobMala() },
                    isVibration = isVibration
                )

            }
        }
    }//on create===============================

    private fun init(){

        tracker = TrackScreen(this)

        presenter = BoishnobMalaPresenter(this)
    }

    override fun onStart() {
        super.onStart()

        tracker.start(ACTIVITY.Act_boisnob_mala)

        tracker.send()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_boisnob_mala)
    }

    override fun malaList(list: List<BoishnobItem>) {
        mantraList.clear()
        mantraList.addAll(list)

    }

}//class========================================

@Preview(showBackground = true)
@Composable
private fun BoisnobMalaFullScreen(
    isDark: Boolean = false,
    backClick: () -> Unit = {},
    mantraList : List<BoishnobItem> = emptyList(),
    floatingButtonClick : () -> Unit = {},
    isVibration : Boolean = false
) {

    var isMantraDialogVisible by remember { mutableStateOf(false) }

    Scaffold(

        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() }
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

            /* counter */
            ComposeHelper().Counter(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.Center),
                counterLimit = 50000L,
                currentCount = {},
                isDark = isDark,
                isVibrationEnabled = isVibration
            )

            /* floating button */

            FloatingButton(
                modifier = Modifier
                    .wrapContentWidth()
                    .align(Alignment.BottomEnd),
                isDark = isDark,
                onClick = {
                    floatingButtonClick()
                    isMantraDialogVisible = true
                }
            )

            /* mantra dialog */

            if (isMantraDialogVisible){

                MantraDialog(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.5f)),
                    isDark = isDark,
                    mantraList = mantraList,
                    closeClick = { isMantraDialogVisible = false }
                )

            }


        }//box

    }//scaffold


}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
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
                painter = painterResource(com.mala.digital_joper_mala.R.drawable.ic_back),
                contentDescription = "Back",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }


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

            Text( text = "বৈষ্ণব মন্ত্র",
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
private fun MantraDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    mantraList : List<BoishnobItem> = emptyList(),
    closeClick: () -> Unit = {},
) {

    val lazState = rememberLazyListState()

    Box(

        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null
            ) {}
            .padding(9.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .height(550.dp)
                .clip(shape = RoundedCornerShape(18.dp))
                .clickable(
                    indication = null,
                    interactionSource = null
                ) {}
                .background(color = if (isDark) Color(0xFF494949) else Color(0xFFFFFFFF))
                .padding(7.dp)
                .align(Alignment.BottomCenter)

        ) {

            Spacer(modifier = Modifier.height(7.dp))

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            ) {

                Text( text = "মন্ত্র সমূহ",
                    fontSize = 18.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
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

            Spacer(modifier = Modifier.height(12.dp))

            LazyColumn(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .align(Alignment.CenterHorizontally),
                state = lazState

            ) {

                items(
                    items = mantraList,
                    key = null
                ){ it ->

                    Item(
                        isDark = isDark,
                        title = it.title,
                        mantra = it.mantra
                    )

                }

                items(
                    count = 1
                ){

                    Spacer(modifier = Modifier.height(5.dp))

                }

            }//lazyColumn

        }//column

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
