package com.mala.digital_joper_mala.View

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.Model.MantraItem
import com.mala.digital_joper_mala.Presenter.AllMantra
import com.mala.digital_joper_mala.Presenter.AllMantraPresenter
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class Act_all_mantra : ComponentActivity(), AllMantra {

    private lateinit var presenter : AllMantraPresenter

    private lateinit var tracker : TrackScreen

    //init
    private var dialogStatus = mutableStateOf(false)
    private val allMantraList = mutableStateListOf<MantraItem>()
    private val favoriteMantraList = mutableStateListOf<MantraItem>()
    private val searchMantraList = mutableStateListOf<MantraItem>()

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

            presenter.getAllMantraCache()

            LaunchedEffect(dialogStatus.value) {

                if (dialogStatus.value){

                    presenter.getAllMantraFromServer()

                }

            }

            presenter.getAllMantra()

            Digital_Joper_malaTheme {

                AllMantraFullScreen(
                    isDark = isDark,
                    backClick = { finish() },
                    alertDialogStatus = dialogStatus.value,
                    alertDialogUserData = { presenter.setAllMantraCache(it) },
                    mantraList = allMantraList,
                    copyMantraClick = {

                        Clipboard.clipData(this, it)

                        ShortMessageHelper.toast(this, "কপি হয়েছে")

                    },
                    searchList = searchMantraList,
                    searchFiled = { presenter.searchMantraInAllMantra(it) },
                    favoriteClick = { presenter.getAllFavoriteMantra() },
                    favoriteMantraList = favoriteMantraList,
                    saveInFavorite = { title, mantra ->
                        presenter.favoriteMantraInsert(title, mantra)
                    },
                    removeFavoriteClick = {
                        presenter.deleteFavoriteMantra(it)
                    }

                )

            }
        }
    }//on create=================================

    private fun init(){

        presenter = AllMantraPresenter(this, this)

        tracker = TrackScreen(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start(ACTIVITY.Act_all_mantra)

        tracker.send()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_all_mantra)
    }

    override fun onDestroy() {
        super.onDestroy()

        presenter.onDestroy()
    }

    override fun dialogStatus(value: Boolean) {
        dialogStatus.value = value

        Log.d("status", value.toString())

    }

    override fun allMantraList(list: List<MantraItem>) {
        allMantraList.clear()
        allMantraList.addAll(list)
    }

    override fun favoriteMantraList(list: List<MantraItem>) {
        favoriteMantraList.clear()
        favoriteMantraList.addAll(list)

    }

    override fun searchMantraList(list: List<MantraItem>) {
        searchMantraList.clear()
        searchMantraList.addAll(list)
    }

    override fun deleteFavoriteMantraStatus(status: String) {
        ShortMessageHelper.toast(this, status)
    }

    override fun insertStatus(status: String) {
        ShortMessageHelper.toast(this, status)
    }

}//class=========================================

@Preview(showBackground = true)
@Composable
private fun AllMantraFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    alertDialogStatus : Boolean = false,
    alertDialogUserData : (Boolean) -> Unit = {},
    mantraList : List<MantraItem> = emptyList(),
    copyMantraClick : (String) -> Unit = {},
    searchList: MutableList<MantraItem> = mutableListOf(),
    searchFiled: (String) -> Unit = {},
    favoriteClick: () -> Unit = {},
    favoriteMantraList : List<MantraItem> = emptyList(),
    saveInFavorite : (title : String, mantra : String) -> Unit = {_, _ ->},
    removeFavoriteClick: (String) -> Unit = {}

) {

    val lazyState = rememberLazyListState()
    var isSearchDialogVisible = remember { mutableStateOf(false) }
    var isAlertDialogVisible = remember { mutableStateOf(true) }
    var isDialogShowing = remember { mutableStateOf(false) }
    var isFavoriteVisible = remember { mutableStateOf(false) }


    LaunchedEffect(alertDialogStatus) {

        if (alertDialogStatus){

            isAlertDialogVisible.value = false

        }

    }

    LaunchedEffect(
        isSearchDialogVisible.value,
        isAlertDialogVisible.value,
        isFavoriteVisible.value
    ) {

        if (isSearchDialogVisible.value){

            isDialogShowing.value = true

        }else if (isAlertDialogVisible.value){

            isDialogShowing.value = true

        } else if (isFavoriteVisible.value){

            isDialogShowing.value = true

        } else{

            isDialogShowing.value = false

        }

    }

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() },
            searchClick = { isSearchDialogVisible.value = true },
            favoriteClick = {
                favoriteClick()
                isFavoriteVisible.value = true
                            },
            isAnyDialogShowing = isDialogShowing.value
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

            if (mantraList.isEmpty()){

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)

                ) {

                    Image( painter = painterResource(R.drawable.img_empty),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(120.dp)
                            .align(Alignment.CenterHorizontally)

                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text( text = "কোন মন্ত্র নেই ।",
                        fontSize = 16.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .wrapContentWidth()
                            .align(Alignment.CenterHorizontally)

                    )

                }//column


            }else{

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth(),
                    state = lazyState

                ) {

                    items(
                        items = mantraList,
                        key = null
                    ){ it ->

                        Item(
                            isDark = isDark,
                            title = it.title,
                            mantra = it.mantra,
                            mantraClick = { copyMantraClick(it.mantra) },
                            favoriteClick = { saveInFavorite(it.title, it.mantra) }

                        )

                    }

                }//lazyColumn

            }


            if (isSearchDialogVisible.value){

                SearchDialog(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.5f)),
                    isDark = isDark,
                    searchFiled = { searchFiled(it) },
                    searchList = searchList,
                    mantraClick = { copyMantraClick(it) },
                    closeClick = { isSearchDialogVisible.value = false },
                    favoriteClick = { title, mantra ->
                         saveInFavorite(title, mantra)
                    }
                )

            }

            if (isFavoriteVisible.value){

                FavoriteDialog(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = if (isDark) Color.White.copy(alpha = 0.5f) else Color.Black.copy(alpha = 0.5f)),
                    isDark = isDark,
                    favoriteList = favoriteMantraList,
                    mantraClick = { copyMantraClick(it) },
                    closeClick = { isFavoriteVisible.value = false },
                    removeFavoriteClick = {removeFavoriteClick(it)}
                )

            }

            if (isAlertDialogVisible.value){

                AlertDialog(
                    modifier = Modifier
                        .fillMaxSize()
                        .background( color = if (isDark) Color.LightGray.copy(alpha = 0.5f) else
                            Color.Black.copy(alpha = 0.5f)
                        ),
                    isDark = isDark,
                    userClick = {

                        alertDialogUserData(true)
                        isAlertDialogVisible.value = false

                    }
                )

            }//condition

        }//box

    }//scaffold

}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
    searchClick : () -> Unit = {},
    favoriteClick : () -> Unit = {},
    isAnyDialogShowing : Boolean = false
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

        val iconList = listOf(
            R.drawable.ic_search,
            R.drawable.ic_favorite
        )

        Row(

            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.CenterEnd)

        ) {

            iconList.forEachIndexed { index, icon ->

                Box(

                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(7.dp)

                ) {

                    IconButton(
                        onClick = {

                            if (!isAnyDialogShowing){

                                when(index){

                                    0 -> searchClick()
                                    1 -> favoriteClick()

                                }

                            }
                        },
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            //.background(color = Color.Green)
                            .align(Alignment.Center)
                            .size(37.dp)
                    ) {

                        Icon(
                            painter = painterResource(icon),
                            contentDescription = "Back",
                            tint = Color(0xFFFFFFFF),
                            modifier = Modifier
                                .wrapContentWidth()

                        )

                    }

                }//box

            }//loop

        }//row

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun Item(
    isDark: Boolean = false,
    title : String = "Test",
    mantra : String = "Test",
    mantraClick : () -> Unit = {},
    favoriteClick : () -> Unit = {},
    @DrawableRes favoriteIcon : Int = R.drawable.ic_outline_bookmark
) {

    var isLongClicked by remember { mutableStateOf(false) }

    var isVisible = remember { mutableStateOf(false) }

    LaunchedEffect(isVisible) {

        while (true){

            delay(5000.milliseconds)

            isVisible.value = true

            delay(5000.milliseconds)

            isVisible.value = false

        }

    }

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(14.dp))
                .clip(shape = RoundedCornerShape(14.dp))
                .combinedClickable(
                    onClick = { isLongClicked = false },
                    onLongClick = { isLongClicked = true },
                    indication = null,
                    interactionSource = null
                )
                .background(color = if (isDark) Color(0xFF626161) else Color(0xFFFFFFFF))
                .padding(5.dp)

        ) {

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            ) {

                Text( text = title,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color(0xFFEAE7E7) else Color(0xFF313030),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .align(Alignment.CenterStart)

                )

                /* favorite button */

                Box(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clip(shape = CircleShape)
                        .clickable{
                            favoriteClick()
                            isLongClicked = false
                        }
                        .size(30.dp)
                        .align(Alignment.CenterEnd)
                ) {

                    if (isLongClicked){

                        Icon( painter = painterResource(favoriteIcon),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFEEEEEE) else Color(0xFF4D4C4C),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(22.dp)
                                .align(Alignment.Center)

                        )

                    }

                }//box

            }//box

            Spacer(modifier = Modifier.height(5.dp))

            /* Mantra */

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable { mantraClick() }
                    .background(color = if (isDark) Color(0xFF8A8989) else Color(0xFFFFF1F1))
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                Text( text = mantra,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .align(Alignment.Center)

                )

                if (isVisible.value){

                    Icon( painter = painterResource(R.drawable.ic_copy),
                        contentDescription = "",
                        tint = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(24.dp)
                            .align(Alignment.CenterEnd)

                    )

                }

            }//box

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun SearchDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    searchFiled : (String) -> Unit = {},
    searchList : MutableList<MantraItem> = mutableListOf(),
    mantraClick: (String) -> Unit = {},
    closeClick : () -> Unit = {},
    favoriteClick: (title : String, mantra : String) -> Unit = {_, _->}
) {

    var searchInput = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val lazState = rememberLazyListState()

    Box(

        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null
            ){}
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
                ){}
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

                Text( text = "মন্ত্র সার্চ",
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
                        .clickable{ closeClick() }
                        .size(30.dp)
                        .align(Alignment.CenterEnd)

                ) {

                    Icon( painter = painterResource(R.drawable.ic_wrong),
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

            /* search input filed */
            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = if (isDark) Color(0xFFCECDCD) else Color(0xFFA4A1A1),
                        shape = RoundedCornerShape(12.dp)
                    )
                    .clip(shape = RoundedCornerShape(12.dp))
                    .padding(5.dp)


            ) {

                if (searchInput.value.isBlank()){

                    Text( text = "মন্ত্র সার্চ করুন",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFC4C3C3) else Color(0xFF676666),
                        textAlign = TextAlign.Start,
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(5.dp)
                            .align(Alignment.CenterStart)

                    )

                }

                BasicTextField(
                    value = searchInput.value,
                    onValueChange = { searchInput.value = it },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(5.dp)
                        .align(Alignment.CenterStart),
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000)
                    ),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Search,
                        keyboardType = KeyboardType.Text
                    ),
                    keyboardActions = KeyboardActions(
                        onSearch = {
                            searchFiled(searchInput.value)
                            keyboardController?.hide()
                                   },

                    ),
                    cursorBrush = SolidColor(if (isDark) Color(0xFF2196F3) else Color(0xFF000000))
                )

                if (searchInput.value.isNotBlank()){

                    Box(
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            .clickable {
                                searchInput.value = ""
                                searchList.clear()
                            }
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_clear),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFE1E1E1) else Color(0xFF3F3E3E),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(22.dp)
                                .align(Alignment.Center)

                        )

                    }

                }

            }//box

            Spacer(modifier = Modifier.height(7.dp))

            if (searchList.isEmpty()){

                Box(

                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)


                ) {

                    Image( painter = painterResource(R.drawable.img_empty_file),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(100.dp)
                            .align(Alignment.Center)

                    )

                }//box

            }else{

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    state = lazState

                ) {

                    items(
                        items = searchList,
                        key = null
                    ){ it ->

                        Item(
                            isDark = isDark,
                            title = it.title,
                            mantra = it.mantra,
                            mantraClick = { mantraClick(it.mantra) },
                            favoriteClick = { favoriteClick(it.title, it.mantra) }
                        )

                    }

                    items(
                        count = 1
                    ){

                        Spacer(modifier = Modifier.height(5.dp))

                    }

                }//lazyColumn

            }

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun FavoriteDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    favoriteList : List<MantraItem> = emptyList(),
    mantraClick: (String) -> Unit = {},
    closeClick: () -> Unit = {},
    removeFavoriteClick: (String) -> Unit = {}
) {

    val lazState = rememberLazyListState()

    Box(

        modifier = modifier
            .fillMaxWidth()
            .clickable(
                indication = null,
                interactionSource = null
            ){}
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
                ){}
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

                Text( text = "পছন্দের মন্ত্র",
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
                        .clickable{ closeClick() }
                        .size(30.dp)
                        .align(Alignment.CenterEnd)

                ) {

                    Icon( painter = painterResource(R.drawable.ic_wrong),
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

            if (favoriteList.isEmpty()){

                Box(

                    modifier = Modifier
                        .fillMaxSize()


                ) {

                    Image( painter = painterResource(R.drawable.img_empty_file),
                        contentDescription = "",
                        modifier = Modifier
                            .wrapContentWidth()
                            .size(100.dp)
                            .align(Alignment.Center)

                    )

                }//box

            }else{

                LazyColumn(

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.CenterHorizontally),
                    state = lazState

                ) {

                    items(
                        items = favoriteList,
                        key = null
                    ){ it ->

                        Item(
                            isDark = isDark,
                            title = it.title,
                            mantra = it.mantra,
                            mantraClick = { mantraClick(it.mantra) },
                            favoriteClick = { removeFavoriteClick(it.mantra) },
                            favoriteIcon = R.drawable.ic_fill_bookmark
                        )

                    }

                    items(
                        count = 1
                    ){

                        Spacer(modifier = Modifier.height(5.dp))

                    }

                }//lazyColumn

            }

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun AlertDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    userClick : () -> Unit = {}
) {

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(17.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = if (isDark) Color(0xFF5D5C5C) else Color(0xFFFFFFFF))
                .padding(15.dp)
                .align(Alignment.BottomCenter)

        ) {

            Text( text = "সর্তকবার্তা",
                fontSize = 18.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Bold,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(text = "দেব-দেবীর বিশেষ শক্তিসম্ভারযুক্ত মন্ত্রসমূহ অত্যন্ত সূক্ষ্ম ও প্রভাবশালী। এই ধরনের মন্ত্র সাধারণত গুরু/আচার্যের দীক্ষা বা অনুমতি নিয়ে জপ করা উচিত।",
                fontSize = 16.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text( text = "ঠিক আছে",
                fontSize = 15.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .wrapContentWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable { userClick() }
                    .padding(9.dp)
                    .align(Alignment.End)
            )

        }//column

    }//box

}//fun end