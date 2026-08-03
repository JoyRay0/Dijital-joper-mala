package com.mala.digital_joper_mala.View

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import androidx.core.net.toUri
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.Model.Tracker
import com.mala.digital_joper_mala.Presenter.Setting
import com.mala.digital_joper_mala.Presenter.SettingPresenter
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*

class Act_setting : ComponentActivity(), Setting {

    private companion object{

        const val APP_PACKAGE_NAME = "com.mala.digital_joper_mala"

    }

    //init===============================
    private lateinit var cacheHelper : CacheHelper_
    private lateinit var presenter : SettingPresenter
    private lateinit var tracker : TrackScreen

    private var themeData = mutableStateOf("")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        presenter.getCache("my_theme", "0")

        setContent {

            var isDark by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme {

                SettingFullScreen(
                    isDark = isDark,
                    backClick = {
                        IntentHelper.normalIntent(this, Act_home::class.java)
                        finishAffinity()
                                },
                    feedback = {

                        val intent = Intent(Intent.ACTION_SENDTO)
                        val uriText = "mailto:" + Uri.encode(" r.k.softwares17@gmail.com")
                        val uri = uriText.toUri()
                        intent.data = uri
                        startActivity(Intent.createChooser(intent, " "))
                        finishAffinity()

                    },
                    newFeature = {

                        startActivity(Intent(this, Act_new_feature::class.java))

                    },
                    appTheme = {

                        presenter.setCache("my_theme", it.toString())
                        recreate()

                               },
                    appLanguage = {},
                    otherApp = {

                        IntentHelper.dataIntent(
                            this,
                            Act_webview::class.java,
                            KeyHelper.getWebViewKey(),
                            ""
                        )

                    },
                    appShare = {

                        val intent = Intent(Intent.ACTION_SEND)
                        intent.type = "text/plain"
                        val Body = "Download this App"
                        val sub =
                            "https://play.google.com/store/apps/details?id=$APP_PACKAGE_NAME"
                        intent.putExtra(Intent.EXTRA_TEXT, Body)
                        intent.putExtra(Intent.EXTRA_TEXT, sub)
                        startActivity(Intent.createChooser(intent, null))

                    },
                    appRating = {

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

                    },
                    appPrivacy = {

                        IntentHelper.dataIntent(
                            this,
                            Act_webview::class.java,
                            KeyHelper.getWebViewKey(),
                            "policy"
                        )

                    },
                    getAppTheme = themeData.value.toInt()
                )

            }

            BackHandler() {

                IntentHelper.normalIntent(this, Act_home::class.java)
                finishAffinity()

            }

        }
    }//on create===============================

    private fun init(){

        cacheHelper = CacheHelper_(this, "Theme")
        presenter = SettingPresenter(this, cacheHelper)
        tracker = TrackScreen(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_setting)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()

    }

    override fun cache(value: String) {
        themeData.value = value
    }

}//class=======================================


@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun SettingFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    feedback : () -> Unit = {},
    newFeature : () -> Unit = {},
    appTheme : (Int) -> Unit = {},
    appLanguage : () -> Unit = {},
    otherApp : () -> Unit = {},
    appShare : () -> Unit = {},
    appRating : () -> Unit = {},
    appPrivacy : () -> Unit = {},
    getAppTheme : Int  = 0
) {

    var isInfoVisible = remember { mutableStateOf(false) }
    var isThemeVisible = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() }
        ) },
        
        modifier = Modifier
            .fillMaxSize()
            .background(color = if (isDark) DarkStatusBar else LightStatusBar)
            .systemBarsPadding()

    ) {innerPadding ->

        Box(

            modifier = Modifier
                .fillMaxSize()
                .background(color = if (isDark) DarkBackground else LightBackground)
                .padding(innerPadding)

        ){

            Column(

                modifier = Modifier
                    .fillMaxWidth()

            ) {

                Spacer(modifier = Modifier.height(7.dp))

                TextButtonHelper(
                    "আমাদের মতামত জানান",
                    icon = R.drawable.ic_feedback,
                    iconSize = 22.dp,
                    btnClick = { feedback() },
                    isDark = isDark,
                    leftTopCornerRadios = 14.dp,
                    rightTopCornerRadios = 14.dp
                )

                Spacer(modifier = Modifier.height(3.dp))

                TextButtonHelper(
                    "নতুন ফিচারগুলো",
                    icon = R.drawable.ic_new_item,
                    iconSize = 22.dp,
                    btnClick = { newFeature() },
                    isDark = isDark,
                    rightBottomCornerRadios = 14.dp,
                    leftBottomCornerRadios = 14.dp
                )

                //=================================
                Spacer(modifier = Modifier.height(17.dp))

                TextButtonHelper(
                    "অ্যাপ থিম",
                    icon = R.drawable.ic_day_night,
                    iconSize = 22.dp,
                    btnClick = { isThemeVisible.value = !isThemeVisible.value },
                    isDark = isDark,
                    leftTopCornerRadios = 14.dp,
                    rightTopCornerRadios = 14.dp
                )

                Spacer(modifier = Modifier.height(3.dp))

                TextButtonHelper(
                    "ভাষা",
                    icon = R.drawable.ic_languages,
                    iconSize = 22.dp,
                    btnClick = { appLanguage() },
                    isDark = isDark,
                    isEnabled = false,
                    rightBottomCornerRadios = 14.dp,
                    leftBottomCornerRadios = 14.dp
                )

                //=======================================

                Spacer(modifier = Modifier.height(17.dp))

                TextButtonHelper(
                    "আমাদের অন্যান্য অ্যাপগুলো",
                    icon = R.drawable.ic_browser,
                    iconSize = 22.dp,
                    btnClick = { otherApp() },
                    isDark = isDark,
                    leftTopCornerRadios = 14.dp,
                    rightTopCornerRadios = 14.dp
                )

                Spacer(modifier = Modifier.height(3.dp))

                TextButtonHelper(
                    "অ্যাপটি শেয়ার করুন",
                    icon = R.drawable.ic_share2,
                    iconSize = 22.dp,
                    btnClick = { appShare() },
                    isDark = isDark,
                )

                Spacer(modifier = Modifier.height(3.dp))

                TextButtonHelper(
                    "আমাদের অ্যাপকে ফাইভ স্টার রেটিং দিন",
                    icon = R.drawable.ic_star_black,
                    iconSize = 22.dp,
                    btnClick = { appRating() },
                    isDark = isDark,
                    rightBottomCornerRadios = 14.dp,
                    leftBottomCornerRadios = 14.dp
                )

                //=================================
                Spacer(modifier = Modifier.height(17.dp))

                TextButtonHelper(
                    "প্রাইভেসি & পলিসি",
                    icon = R.drawable.ic_privacy,
                    iconSize = 22.dp,
                    btnClick = { appPrivacy() },
                    isDark = isDark,
                    leftTopCornerRadios = 14.dp,
                    rightTopCornerRadios = 14.dp
                )

                Spacer(modifier = Modifier.height(3.dp))

                TextButtonHelper(
                    "কিছু ইনফরমেশন",
                    icon = R.drawable.ic_info,
                    iconSize = 22.dp,
                    btnClick = { isInfoVisible.value = true },
                    isDark = isDark,
                    rightBottomCornerRadios = 14.dp,
                    leftBottomCornerRadios = 14.dp
                )

            }//column

            if (isThemeVisible.value){

                ModalBottomSheet(
                    onDismissRequest = { isThemeVisible.value = false },
                    containerColor = if (isDark) DarkSettingItemBackground else LightSettingItemBackground,
                    dragHandle = null
                ) {

                    AppTheme(
                        modifier = Modifier
                            .fillMaxWidth(),
                        modeClick = {

                            appTheme(it)
                            isThemeVisible.value = false

                        },
                        userSelectedMode = getAppTheme,
                        isDark = isDark
                    )

                }

            }

            if (isInfoVisible.value){

                Box(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp)
                        .align(Alignment.BottomCenter)

                ) {

                    Column(

                        modifier = Modifier
                            .fillMaxWidth()
                            .shadow(elevation = 10.dp, shape = RoundedCornerShape(14.dp))
                            .clip(shape = RoundedCornerShape(14.dp))
                            .background(color = if (isDark) DarkSettingItemBackground else LightSettingItemBackground)
                            .padding(5.dp)
                            .align(Alignment.Center)

                    ) {

                        Text( text = "ধন্যবাদ আমাদের অ্যাপ ব্যবহার করার জন্য",
                            fontSize = 17.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.SemiBold,
                            color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF3A3434),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)

                        )

                        Spacer(modifier = Modifier.height(4.dp))

                        Text( text = "আমরা অ্যাপ এর মধ্যে নিয়মিত আপডেট দিই এবং প্রতিটি আপডেটে নতুন নতুন ফিচার যুক্ত করি। আমাদের অ্যাপ ব্যবহার করতে যদি কোনো প্রকার সমস্যা হয় তবে দয়া করে আমাদের জানান। আমরা আপনার সমস্যা সমাধানের যথাসাধ্য চেষ্টা করবো।",
                            fontSize = 15.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFFD5D5D5) else Color(0xFF000000),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(5.dp)
                                .align(Alignment.CenterHorizontally)

                        )

                        Spacer(modifier = Modifier.height(7.dp))
                        
                        Text( text = "ঠিক আছে",
                            fontSize = 13.sp,
                            fontFamily = BanglaHelper.banglaFont(),
                            fontWeight = FontWeight.Normal,
                            color = if (isDark) Color(0xFFFFDDDD) else Color(0xFF3A3434),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .wrapContentWidth()
                                .clip(shape = RoundedCornerShape(12.dp))
                                .clickable(
                                    indication = ripple(
                                        bounded = true,
                                        color = if (isDark) Color(0xFFD3CFCF) else Color(0xFF3D3838),
                                        radius = 40.dp
                                    ),
                                    interactionSource = remember { MutableInteractionSource() }
                                ) { isInfoVisible.value = false }
                                .padding(start = 9.dp, end = 9.dp, top = 5.dp, bottom = 5.dp)
                                .align(Alignment.End)

                        )

                        Spacer(modifier = Modifier.height(5.dp))

                    }//column

                }//box

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
                painter = painterResource(R.drawable.ic_back),
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
private fun TextButtonHelper(
    text : String = "Test Icon",
    icon : Int = 0,
    iconSize : Dp = 20.dp,
    btnClick : () -> Unit = {},
    isEnabled : Boolean = true,
    isDark: Boolean = false,
    leftTopCornerRadios : Dp = 5.dp,
    leftBottomCornerRadios : Dp = 5.dp,
    rightTopCornerRadios : Dp = 5.dp,
    rightBottomCornerRadios : Dp = 5.dp
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 5.dp, end = 5.dp)

    ) {

        Box(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 4.dp,
                    shape = RoundedCornerShape(
                        topStart = leftTopCornerRadios,
                        bottomStart = leftBottomCornerRadios,
                        topEnd = rightTopCornerRadios,
                        bottomEnd = rightBottomCornerRadios
                    )
                )
                .clip(
                    shape = RoundedCornerShape(
                        topStart = leftTopCornerRadios,
                        bottomStart = leftBottomCornerRadios,
                        topEnd = rightTopCornerRadios,
                        bottomEnd = rightBottomCornerRadios
                    )
                )
                .clickable(
                    enabled = isEnabled
                ) { btnClick() }
                .background(color = if (isDark) DarkSettingItemBackground else LightSettingItemBackground)
                .alpha(alpha = if (isEnabled) 1f else 0.5f)
                //.padding(10.dp)
                .align(Alignment.Center)

        ) {

            Row(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(12.dp)
                    .align(Alignment.CenterStart)

            ) {

                if (!icon.toString().isEmpty()){

                    Icon( painter = painterResource(icon),
                        contentDescription = "Feedback",
                        tint = if (isDark) DarkDefaultIconColor else LightDefaultIconColor,
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(iconSize)
                            .align(Alignment.CenterVertically)

                    )

                }

                Spacer(modifier = Modifier.width(19.dp))

                Text( text = text,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) DarkSettingText else LightSettingText,
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterVertically)
                )

            }//row

            Box(

                modifier = Modifier
                    .wrapContentWidth()
                    .padding(12.dp)
                    .align(Alignment.CenterEnd)

            ) {

                Icon( painter = painterResource(R.drawable.ic_right),
                    contentDescription = "Forward",
                    tint = if (isDark) Color(0xFF34ADA1) else Color(0xC6534848),
                    modifier = Modifier
                        .wrapContentWidth()
                        .size(22.dp)
                        .align(Alignment.Center)

                )

            }//box

        }//box

    }//box
    
}//fun end


@Preview(showBackground = true)
@Composable
private fun AppTheme(
    modifier: Modifier = Modifier,
    modeClick : (Int) -> Unit = {},
    userSelectedMode : Int = 0,
    isDark: Boolean = false
) {

    var themeIndex = remember(userSelectedMode) { mutableStateOf(userSelectedMode) }

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                //.shadow(elevation = 5.dp, shape = RoundedCornerShape(16.dp))
                //.clip(shape = RoundedCornerShape(16.dp))
                //.background(color = if (isDark) DarkSettingItemBackground else LightSettingItemBackground)
                .padding(9.dp)

        ) {

            val themeText = arrayOf("সিস্টেম ডিফল্ট", "ডার্ক মোড", "লাইট মোড")

            themeText.forEachIndexed { index, text ->

                Row(

                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable{
                            themeIndex.value = index
                            modeClick(index)
                        }
                        .background(color = if (themeIndex.value == index){

                            if (isDark){

                                Color(0xFF947D7D)

                            }else{

                                Color(0xFFFFEBEB)

                            }

                        } else Color.Transparent)
                        .padding(7.dp)
                        .align(Alignment.Start)

                ) {

                    if (themeIndex.value == index){

                        Icon( painter = painterResource(R.drawable.ic_ok),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(21.dp)
                                .align(Alignment.CenterVertically)

                        )

                    }else{

                        Spacer(modifier = Modifier.width(22.dp))

                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Text( text = text,
                        fontSize = 14.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(3.dp)
                            .align(Alignment.CenterVertically)

                    )


                }//row

            }//loop

        }//column

    }//box
    
}//fun end