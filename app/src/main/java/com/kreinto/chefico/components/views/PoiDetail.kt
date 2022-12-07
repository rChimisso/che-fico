package com.kreinto.chefico.components.views

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.twotone.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.RoundButton


@Composable
fun HorizontalPagerSlider(
  modifier: Modifier = Modifier,
  currentPage: Int,
  count: Int,
) {
  LazyRow(
    modifier = modifier
      .padding(8.dp)
      .fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceBetween
  ) {
    items(count) { page ->
      if (page == currentPage) {
        Box(
          modifier = Modifier
            .fillParentMaxWidth((1f / count) - ((count - 1) * 0.001f))
            .height(2.dp)
            .background(Color.White)
        )
      } else {
        Box(
          modifier = Modifier
            .fillParentMaxWidth((1f / count) - ((count - 1) * 0.001f))
            .height(2.dp)
            .background(Color.LightGray.copy(0.5f))
        )
      }
    }
  }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun PoiDetailView() {
  Scaffold(
    topBar = {
      TopAppBar(
        scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(),
        navigationIcon = {
          RoundButton(
            icon = Icons.TwoTone.ArrowBack,
            contentDescriptor = "",
            onClick = {},
            color = Color.White,
          )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
          containerColor = Color.Transparent
        ),
        actions = {},
        title = {}
      )
    },
    bottomBar = {},
    content = {
      Column {
        Surface(
          shadowElevation = 12.dp,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column {
            Box {
              val pagerState = rememberPagerState()
              HorizontalPager(
                state = pagerState,
                count = 5,
                modifier = Modifier
                  .fillMaxWidth()
                  .height(128.dp)
                  .align(Alignment.TopCenter),
              ) {
                Image(
                  contentScale = ContentScale.Crop,
                  painter = painterResource(id = R.drawable.spiral),
                  contentDescription = "$it"
                )
              }
              HorizontalPagerSlider(
                modifier = Modifier
                  .fillMaxWidth()
                  .background(Color.Transparent)
                  .align(Alignment.TopCenter),
                currentPage = pagerState.currentPage,
                count = 5
              )
            }

            Row {
              TextField(
                value = "Nome pianta",
                onValueChange = {},
                maxLines = 1,
                singleLine = true,
                modifier = Modifier.weight(1f),
                textStyle = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
                colors = TextFieldDefaults.textFieldColors(
                  textColor = Color(0xff4caf50),
                  cursorColor = Color.Black,
                  containerColor = Color.Transparent,
                  focusedIndicatorColor = Color.Transparent,
                  unfocusedIndicatorColor = Color.Transparent,
                  disabledIndicatorColor = Color.Transparent
                )
              )
              val icons = arrayOf(
                Icons.Default.ShoppingCart,
                Icons.Default.Share,
                Icons.Default.Call
              )
              for (i in 0..2) {
                Spacer(modifier = Modifier.width(8.dp))
                RoundButton(
                  modifier = Modifier.offset(y = -(20).dp),
                  icon = icons[i],
                  onClick = {}
                )
              }
              Spacer(modifier = Modifier.width(8.dp))
            }
          }
        }
      }
    }
  )
}

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
@Preview
fun PoiDetailPreviw() {
  PoiDetailView()
}