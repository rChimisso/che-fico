package com.kreinto.chefico.views.poicreation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.room.CheFicoViewModel

@ExperimentalMaterial3Api
@Composable
fun PoiCreationView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  latitude: Double? = null,
  longitude: Double? = null
) {
  SimpleFrame(
    onBackPressed = onNavigate,
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = R.drawable.ic_close,
          contentDescription = "Cancel",
          colors = IconButtonDefaults.filledIconButtonColors(
            contentColor = Color.Red
          )
        ) {},
        rightButtonData = ButtonData(
          icon = R.drawable.ic_check,
          contentDescription = "Confirm"
        ) {}
      )
    }
  ) {
    SimpleFrame(
      onBackPressed = onNavigate,
    ) {
//      if (poiId != null) {
//        val poi = viewModel.getPoi(poiId.toInt()).collectAsStateWithLifecycle(Poi.NullPoi)
//        Column {
//          if (poi.value != Poi.NullPoi) {
//            var name by rememberSaveable { mutableStateOf(poi.value.name) }
//            var description by rememberSaveable { mutableStateOf(poi.value.description) }
//            var type by rememberSaveable { mutableStateOf(poi.value.type) }
//            Surface(
//              shadowElevation = 12.dp,
//              modifier = Modifier.fillMaxWidth(),
//            ) {
//              Column {
//                PoiDetailSlideShow()
//                TextInput(
//                  init = name,
//                  textColor = Color(0xff4caf50),
//                  textStyle = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold
//                  ),
//                  onFocusChanged = {
//                    poi.value.name = name
//                    viewModel.updatePoi(poi.value)
//                  },
//                  onValueChange = { name = it }
//                )
//              }
//            }
//            Surface(
//              shadowElevation = 12.dp,
//              modifier = Modifier.padding(16.dp),
//              shape = RoundedCornerShape(10.dp)
//            ) {
//              Column {
//                TextInput(
//                  modifier = Modifier.requiredHeight(128.dp),
//                  init = description,
//                  textStyle = TextStyle(fontSize = 18.sp),
//                  onFocusChanged = {
//                    poi.value.description = description
//                    viewModel.updatePoi(poi.value)
//                  },
//                  onValueChange = { description = it }
//                )
//                Divider(modifier = Modifier.height(2.dp))
//                Row(
//                  modifier = Modifier.height(56.dp),
//                  verticalAlignment = Alignment.CenterVertically,
//                ) {
//                  Spacer(modifier = Modifier.width(16.dp))
//                  Icon(imageVector = Icons.Default.PlayArrow, contentDescription = "")
//                  Spacer(modifier = Modifier.width(16.dp))
//                  TextInput(
//                    init = type,
//                    textStyle = TextStyle(fontSize = 18.sp),
//                    onFocusChanged = {
//                      poi.value.type = type
//                      viewModel.updatePoi(poi.value)
//                    },
//                    onValueChange = { type = it }
//                  )
//                }
//              }
//            }
//          }
//        }
//      }
    }
  }
}
