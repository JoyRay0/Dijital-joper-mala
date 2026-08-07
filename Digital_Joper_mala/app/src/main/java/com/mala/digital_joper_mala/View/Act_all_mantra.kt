package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BasicTooltipDefaults
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import com.mala.digital_joper_mala.View.main_theme_ui.theme.Digital_Joper_malaTheme

class Act_all_mantra : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            var isDark by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme {

                AllMantraFullScreen(
                    isDark = isDark
                )

            }
        }
    }//on create=================================

}//class=========================================

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AllMantraFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    mantraList : List<MantraItem> = emptyList(),
    mantraClick : (String) -> Unit = {},
    searchList: List<MantraItem> = emptyList(),
    searchFiled: (String) -> Unit = {}
) {

    val lazyState = rememberLazyListState()
    var isSearchDialogVisible = remember { mutableStateOf(false) }

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() },
            searchClick = { isSearchDialogVisible.value = true },
            loadClick = {}
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
                        mantraClick = { mantraClick(it.mantra) }

                    )

                }

            }//lazyColumn

            if (isSearchDialogVisible.value){

                ModalBottomSheet(
                    onDismissRequest = { isSearchDialogVisible.value = false },
                    containerColor = if (isDark) Color(0xFF5D5C5C) else Color(0xFFFFFFFF),
                    dragHandle = null
                ) {

                    Spacer(modifier = Modifier.height(7.dp))

                    SearchDialog(
                        isDark = isDark,
                        searchFiled = { searchFiled(it) },
                        searchList = searchList,
                        mantraClick = { mantraClick(it) }
                    )

                }

            }

        }//box

    }//scaffold

}//fun end

@Preview(showBackground = true)
@Composable
private fun Toolbar(
    isDark : Boolean = false,
    backClick : () -> Unit = {},
    searchClick : () -> Unit = {},
    loadClick : () -> Unit = {}
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
            R.drawable.ic_refresh
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

                            when(index){

                                0 -> searchClick()
                                1 -> loadClick()

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
) {

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(14.dp))
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = if (isDark) Color(0xFF757474) else Color(0xFFFFFFFF))
                .padding(5.dp)

        ) {

            Text( text = title,
                fontSize = 15.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFEAE7E7) else Color(0xFF313030),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(3.dp)
                    .align(Alignment.CenterHorizontally)

            )

            Spacer(modifier = Modifier.height(5.dp))

            /* Mantra */

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(12.dp))
                    .clickable { mantraClick() }
                    .background(color = if (isDark) Color(0xFFC0B1B1) else Color(0xFFFFF5F5))
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                Text( text = mantra,
                    fontSize = 14.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.SemiBold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)

                )

            }//box

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun SearchDialog(
    isDark: Boolean = false,
    searchFiled : (String) -> Unit = {},
    searchList : List<MantraItem> = emptyList(),
    mantraClick: (String) -> Unit = {}
) {

    var searchInput = remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val lazState = rememberLazyListState()


    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
            .imePadding()

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                .padding(7.dp)

        ) {

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
                            //.background(color = Color(0xFFDEDCDC))
                            .clickable{ searchInput.value = "" }
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_wrong),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFE1E1E1) else Color(0xFF3F3E3E),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(15.dp)
                                .align(Alignment.Center)

                        )

                    }

                }

            }//box

            Spacer(modifier = Modifier.height(7.dp))

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
                        mantraClick = { mantraClick(it.mantra) }
                    )

                }

            }//lazyColumn

        }//column

    }//box

}//fun end