package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.utils.HorizontalPagerSlider

@ExperimentalPagerApi
@Composable
fun PoiDetailSlideShow() {
  Box {
    val pagerState = rememberPagerState()
    HorizontalPager(
      state = pagerState,
      count = 10,
      modifier = Modifier
        .fillMaxWidth()
        .height(160.dp)
        .align(Alignment.TopCenter),
    ) {
      Image(
        contentScale = ContentScale.Crop,
        painter = painterResource(id = R.drawable.spiral),
        contentDescription = "$it"
      )
    }
    val icons = arrayOf(
      R.drawable.ic_share,
      R.drawable.ic_snooze // FIXME: Prima era MailOutline, cosa dovrebbe essere?
    )
    LazyRow(
      horizontalArrangement = Arrangement.End,
      modifier = Modifier
        .align(Alignment.BottomEnd)
        .fillMaxWidth()
        .offset(y = 20.dp)
    ) {
      items(icons.size) { index ->
        FilledButton(icon = icons[index], contentDescription = "") {}
        Spacer(modifier = Modifier.width(8.dp))
      }
    }
    HorizontalPagerSlider(
      modifier = Modifier
        .fillMaxWidth()
        .background(Color.Transparent)
        .align(Alignment.TopCenter),
      currentPage = pagerState.currentPage,
      count = 10
    )
  }
}