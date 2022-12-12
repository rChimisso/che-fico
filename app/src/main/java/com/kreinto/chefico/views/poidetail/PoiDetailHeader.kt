package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.RoundButton
import com.kreinto.chefico.components.data.ButtonData
import com.kreinto.chefico.components.utils.HorizontalPagerSlider

@ExperimentalPagerApi
@Composable
fun PoiDetailHeader() {
  Surface(
    shadowElevation = 12.dp,
    modifier = Modifier.fillMaxWidth(),
  ) {
    Column {
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
          Icons.Default.Share,
          Icons.Default.MailOutline
        )
        LazyRow(
          horizontalArrangement = Arrangement.End,
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .fillMaxWidth()
            .offset(y = 20.dp)
        ) {
          items(icons.size) { index ->
            RoundButton(ButtonData(icon = icons[index], contentDescription = "") {})
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
      Text(
        text = "Nome pianta lunghissimo",
        maxLines = 1,
        modifier = Modifier
          .height(56.dp)
          .fillMaxWidth()
          .padding(16.dp),
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xff4caf50),
        overflow = TextOverflow.Ellipsis
      )
    }
  }
}
