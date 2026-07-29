package com.mala.digital_joper_mala.Helper

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mala.digital_joper_mala.Model.HomeData
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

class PagerHelper {


    @Preview(showBackground = true)
    @Composable
    fun Pager(
        modifier: Modifier = Modifier,
        list: List<HomeData> = emptyList(),
        placeHolder : Painter? = null,
        height : Dp = 120.dp
    ) {

        val pagerState = rememberPagerState(
            initialPage = 1000,
            pageCount = { Int.MAX_VALUE }
        )

        Box(

            modifier = modifier
                .fillMaxWidth()

        ) {


            LaunchedEffect(Unit) {

                while (true){

                    delay(5000.milliseconds)

                    if (!pagerState.isScrollInProgress){

                        pagerState.animateScrollToPage(
                            pagerState.currentPage + 1
                        )

                    }

                }

            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(height)
                    .shadow(elevation = 2.dp, shape = RoundedCornerShape(12.dp))
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(color = Color(0xFFFFFFFF))

            ) { page ->

                Box(

                    modifier = Modifier
                        .fillMaxWidth()

                ) {

                    AsyncImage( model = list[page % list.size].image,
                        contentDescription = "",
                        placeholder = placeHolder,
                        error = placeHolder,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()

                    )

                }//box

            }//pager

        }//box

    }//fun end

}