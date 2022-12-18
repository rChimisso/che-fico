package com.kreinto.chefico.views.poidetail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.accompanist.pager.ExperimentalPagerApi
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi


@OptIn(ExperimentalLifecycleComposeApi::class)
@ExperimentalFoundationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun PoiDetailView(
  poiId: String?,
  viewModel: CheFicoViewModel,
  onNavigate: (route: String) -> Unit
) {
  println("CAZZO")
  var poi: State<Poi?>? = null
  if (poiId != null) {
    poi = viewModel.getPoi(poiId.toInt()).collectAsStateWithLifecycle(initialValue = null)
  }
  SimpleFrame(
    onClick = {
      onNavigate(AppRoute.PoiList.route)
    },
  ) { padding ->
    Column(Modifier.padding(top = 0.dp, bottom = padding.calculateBottomPadding())) {
      if (poiId != null && poi != null && poi.value != null) {

        var name by rememberSaveable { mutableStateOf(poi.value!!.name) }

        Surface(
          shadowElevation = 12.dp,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column {
            PoiDetailSlideShow()
            TextField(
              value = name,
              maxLines = 1,
              modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .onFocusChanged {
                  poi.value!!.name = name
                  viewModel.updatePoi(poi.value!!)
                },
              colors = TextFieldDefaults.textFieldColors(
                textColor = Color(0xff4caf50)
              ),
              onValueChange = {
                name = it
              }
            )
          }
        }
        Surface(
          shadowElevation = 12.dp,
          modifier = Modifier.padding(16.dp),
          shape = RoundedCornerShape(10.dp)
        ) {
          Column {
            TextField(
              modifier = Modifier
                .fillMaxWidth()
                .requiredHeight(128.dp),
              textStyle = TextStyle(
                fontSize = 18.sp
              ),
              colors = TextFieldDefaults.textFieldColors(
                textColor = Color.Black,
                cursorColor = Color.Black,
                containerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
              ),
              value = "",
              onValueChange = {
              }
            )

            Divider(modifier = Modifier.height(2.dp))

            Row(
              modifier = Modifier.height(56.dp),
              verticalAlignment = Alignment.CenterVertically,
            ) {
              Spacer(modifier = Modifier.width(16.dp))
              Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
              Spacer(modifier = Modifier.width(32.dp))

              TextField(
                value = "",
                onValueChange = {
                }
              )
            }
          }
        }
      }
    }
  }
}
