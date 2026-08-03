package com.mala.digital_joper_mala.View

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
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
import androidx.compose.ui.unit.*
import com.mala.digital_joper_mala.Database.UserMantraDatabase
import com.mala.digital_joper_mala.Helper.*
import com.mala.digital_joper_mala.Model.UserMantra
import com.mala.digital_joper_mala.Presenter.UserMantraPresenter
import com.mala.digital_joper_mala.Presenter.UserMantras
import com.mala.digital_joper_mala.R
import com.mala.digital_joper_mala.View.main_theme_ui.theme.*
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter

class Act_add_mantra : ComponentActivity(), UserMantras {

    private lateinit var presenter : UserMantraPresenter

    private lateinit var tracker : TrackScreen

    private lateinit var userMantraDatabase : UserMantraDatabase

    //init==============
    private var userMantraList = mutableStateListOf<UserMantra>()
    private var deleteStatus = mutableStateOf("")
    private var isFirstLoaded = mutableStateOf(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        init()

        setContent {

            presenter.getAllMantras()

            var isDark by remember { mutableStateOf(false) }

            if (ThemeHelper.isDarkTheme(this)) isDark = true else isDark = false

            ThemeHelper.SystemUi(
                statusBarColor = if (isDark) DarkStatusBar else LightStatusBar,
                navColor = if (isDark) Color.Black else Color.White,
                darkIcons = false
            )

            Digital_Joper_malaTheme{

                AddMantraFullScreen(
                    isDark = isDark,
                    backClick = { finish() },
                    mantraList = userMantraList,
                    addUserMantra = {
                        presenter.insert(
                            it.title,
                            it.mantra
                        )

                    },
                    deleteClick = {

                        presenter.deleteOne(it)

                    },
                    mantraLongClick = {

                        Clipboard.clipData(this, it)

                        ShortMessageHelper.toast(this, "কপি হয়েছে")

                    },
                )

            }
        }
    }//create======================================

    private fun init(){

        userMantraDatabase = UserMantraDatabase(this)

        presenter = UserMantraPresenter(userMantraDatabase, this)

        tracker = TrackScreen(this)

    }

    override fun onStart() {
        super.onStart()

        tracker.start()
    }

    override fun onStop() {
        super.onStop()

        tracker.stop(ACTIVITY.Act_add_mantra)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }

    override fun getMantra(list: List<UserMantra>) {

        userMantraList.clear()
        userMantraList.addAll(list)

    }

    override fun status(message: String) {
        deleteStatus.value = message
    }

}//class===========================================

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun AddMantraFullScreen(
    isDark : Boolean = false,
    backClick: () -> Unit = {},
    mantraList : List<UserMantra> = emptyList(),
    addUserMantra : (UserMantra) -> Unit = {},
    deleteClick: (String) -> Unit = {},
    mantraLongClick: (String) -> Unit = {},
) {

    var isAddMantraDialogVisible = remember { mutableStateOf(false) }
    val lazyState = rememberLazyListState()

    Scaffold(
        topBar = { Toolbar(
            isDark = isDark,
            backClick = { backClick() },
            addMantraClick = { isAddMantraDialogVisible.value = true }
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
                .imePadding()

        ) {

            if (mantraList.isEmpty()){

                Column(

                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)

                ) {

                    Image( painter = painterResource(R.drawable.img_empty_folder),
                        contentDescription = "Empty",
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(90.dp)
                            .align(Alignment.CenterHorizontally)

                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(text = "কোন মন্ত্র খুজে পাওয়া যায়নি।",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
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
                        //key = {}
                    ){ it ->

                        Item(
                            title = it.title,
                            mantra = it.mantra,
                            deleteClick = {deleteClick(it.mantra)},
                            mantraLongClick = {mantraLongClick(it.mantra)}
                        )

                    }

                }//lazy column

            }

            if (isAddMantraDialogVisible.value){

                ModalBottomSheet (
                    onDismissRequest = { isAddMantraDialogVisible.value = false },
                    containerColor = if (isDark) Color(0xFF644646) else Color(0xFFFFFFFF),
                    dragHandle = null
                ) {

                    AddDialog(
                        modifier = Modifier
                            .fillMaxWidth(),
                        isDark = isDark,
                        closeClick = { isAddMantraDialogVisible.value = false },
                        addMantraClick = {

                            isAddMantraDialogVisible.value = false

                        },
                        inputFiled = { addUserMantra(it) }

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
    addMantraClick : () -> Unit = {}
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

        IconButton(
            onClick = addMantraClick,
            modifier = Modifier
                .wrapContentWidth()
                .clip(shape = CircleShape)
                //.background(color = Color.Green)
                .align(Alignment.CenterEnd)
                .size(37.dp)
        ) {

            Icon(
                painter = painterResource(R.drawable.ic_user_add),
                contentDescription = "Add",
                tint = Color(0xFFFFFFFF),
                modifier = Modifier
                    .wrapContentWidth()

            )

        }

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun AddDialog(
    modifier: Modifier = Modifier,
    isDark: Boolean = false,
    closeClick : () -> Unit = {},
    addMantraClick : () -> Unit = {},
    inputFiled : (UserMantra) -> Unit = {},
) {

    var name = remember { mutableStateOf("") }
    var mantra = remember { mutableStateOf("") }
    val focus = remember { FocusRequester() }

    Box(

        modifier = modifier
            .fillMaxWidth()
            .padding(7.dp)

    ) {

        Column(

            modifier = Modifier
                .fillMaxWidth()
                //.shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
                //.clip(shape = RoundedCornerShape(16.dp))
                //.background(color = if (isDark) Color(0xFF644646) else Color(0xFFFFFFFF))
                .padding(9.dp)

        ) {

            //name
            Text( text = "১। পছন্দের দেব-দেবীর নাম",
                fontSize = 15.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            )

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD7B1B1),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                if (name.value.isBlank()){

                    Text( text = "নাম....",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = if (isDark) Color(0xFFCECECE) else Color(0xFF795F5F),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(2.dp)
                            .align(Alignment.CenterStart)

                    )

                }

                BasicTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    textStyle = TextStyle(
                        fontFamily = BanglaHelper.banglaFont(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color =  if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Next
                    ),
                    cursorBrush = if (isDark) SolidColor(Color(0xFF00BCD4)) else SolidColor(Color(0xFF3F51B5)),
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .focusRequester(focus)
                        .padding(2.dp)
                        .align(Alignment.CenterStart)
                )

                if (name.value.isNotEmpty()){

                    IconButton(
                        onClick = { name.value = "" },
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            //.background(color = Color.LightGray)
                            .size(27.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_clear),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFD7D5D5) else Color(0xFF5E4F4F),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(19.dp)
                                .align(Alignment.Center)

                        )

                    }

                }

            }//box
            
            Spacer(modifier = Modifier.height(12.dp))

            //mantra
            Text( text = "২। পছন্দের মন্ত্র",
                fontSize = 15.sp,
                fontFamily = BanglaHelper.banglaFont(),
                fontWeight = FontWeight.Normal,
                color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)

            )

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD7B1B1),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .clip(shape = RoundedCornerShape(10.dp))
                    .padding(6.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                if (mantra.value.isBlank()){

                    Text( text = "মন্ত্র....",
                        fontSize = 15.sp,
                        fontFamily = BanglaHelper.banglaFont(),
                        fontWeight = FontWeight.Normal,
                        textAlign = TextAlign.Start,
                        color = if (isDark) Color(0xFFCECECE) else Color(0xFF795F5F),
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(2.dp)
                            .align(Alignment.CenterStart)

                    )

                }

                BasicTextField(
                    value = mantra.value,
                    onValueChange = { mantra.value = it },
                    textStyle = TextStyle(
                        fontFamily = BanglaHelper.banglaFont(),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal,
                        color =  if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    ),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    cursorBrush = if (isDark) SolidColor(Color(0xFF00BCD4)) else SolidColor(Color(0xFF3F51B5)),
                    modifier = Modifier
                        .fillMaxWidth(0.92f)
                        .padding(2.dp)
                        .align(Alignment.CenterStart)
                )

                if (mantra.value.isNotEmpty()){

                    IconButton(
                        onClick = { mantra.value = "" },
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            //.background(color = Color.LightGray)
                            .size(27.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_clear),
                            contentDescription = "",
                            tint = if (isDark) Color(0xFFD7D5D5) else Color(0xFF5E4F4F),
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

            Spacer(modifier = Modifier.height(14.dp))

            Row(

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(7.dp)
                    .align(Alignment.CenterHorizontally)

            ) {

                Text( text = "বাতিল করুন",
                    fontSize = 14.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable(
                            enabled = true
                        ) {
                            name.value = ""
                            mantra.value = ""
                            closeClick()
                        }
                        .background(color = Color(0xFFFD675B))
                        .padding(7.dp)
                        .align(Alignment.CenterVertically)

                )

                Spacer(modifier = Modifier.width(14.dp))

                Text( text = "যোগ করুন",
                    fontSize = 14.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(10.dp))
                        .clickable(
                            enabled = if (name.value.isBlank() || mantra.value.isBlank()) false else true
                        ) {
                            inputFiled(
                                UserMantra(
                                    title = SanitizeHelper.sanitizeText(name.value),
                                    mantra = SanitizeHelper.sanitizeText(mantra.value)
                                )
                            )

                            name.value = ""
                            mantra.value = ""

                            addMantraClick()

                        }
                        .alpha(alpha = if (name.value.isBlank() || mantra.value.isBlank()) 0.5f else 1f)
                        .background(color = Color(0xFF94C75A))
                        .padding(7.dp)
                        .align(Alignment.CenterVertically)

                )

            }//row

        }//column

    }//box

}//fun end

@Preview(showBackground = true)
@Composable
private fun Item(
    title : String = "Test",
    mantra : String = "Mantra",
    deleteClick : () -> Unit = {},
    isDark: Boolean = false,
    mantraLongClick : () -> Unit = {}
) {

    var isDeleteVisible = remember { mutableStateOf(false) }

    val isDark = true

    Box(

        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)

    ) { 
        
        Column(

            modifier = Modifier
                .fillMaxWidth()
                .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                .clip(shape = RoundedCornerShape(12.dp))
                .combinedClickable(
                    onClick = { isDeleteVisible.value = false },
                    onLongClick = { isDeleteVisible.value = true },
                    indication = null,
                    interactionSource = null
                )
                .background(color = if (isDark) Color(0xFF4F4A4A) else Color(0xFFFFFFFF))
                .padding(9.dp)
                .align(Alignment.Center)

        ) {

            //===============================
            // user mantra title & delete
            //===============================

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)

            ) {

                Text(text = title,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Normal,
                    color = if (isDark) Color(0xFFE5E4E4) else Color(0xFF000000),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxWidth(0.90f)
                        .padding(3.dp)
                        .align(Alignment.CenterStart)

                )

                if (isDeleteVisible.value){

                    IconButton(
                        onClick = {
                            deleteClick()
                            isDeleteVisible.value = false
                                  },
                        modifier = Modifier
                            .wrapContentWidth()
                            .clip(shape = CircleShape)
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                    ) {

                        Icon( painter = painterResource(R.drawable.ic_delete),
                            contentDescription = "Delete",
                            tint = if (isDark) Color(0xFFE0E0E0) else Color(0xFF5E5656),
                            modifier = Modifier
                                .wrapContentWidth()
                                .size(20.dp)
                                .align(Alignment.Center)

                        )


                    }

                }

            }//box

            Spacer(modifier = Modifier.height(7.dp))

            //============================
            // user mantra
            //============================

            Box(

                modifier = Modifier
                    .fillMaxWidth()
                    .clip(shape = RoundedCornerShape(14.dp))
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {mantraLongClick()}
                    )
                    .background(color = if (isDark) Color(0xFF363636) else Color(0xFFEAEAEA))
                    .padding(2.dp)
                    .align(Alignment.CenterHorizontally)

            ) {
                
                Text(text = mantra,
                    fontSize = 15.sp,
                    fontFamily = BanglaHelper.banglaFont(),
                    fontWeight = FontWeight.Bold,
                    color = if (isDark) Color(0xFFFFFFFF) else Color(0xFF000000),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                        .align(Alignment.Center)
                    )

            }//box


        }//column
        
    }//box

}//fun end