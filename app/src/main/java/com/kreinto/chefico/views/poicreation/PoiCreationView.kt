package com.kreinto.chefico.views.poicreation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.frames.bottombars.SimpleBottomBar
import com.kreinto.chefico.components.misc.PoiDetailContent
import com.kreinto.chefico.room.CheFicoViewModel

@ExperimentalMaterial3Api
@Composable
fun PoiCreationView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel
) {
  var creatingPoi = viewModel.getCreatingPoi()
  val action = {
    viewModel.removeCreatingPoi()
    onNavigate(CheFicoRoute.Back.path)
  }
  SimpleFrame(
    onBackPressed = onNavigate,
    bottomBar = {
      SimpleBottomBar(
        leftButtonData = ButtonData(
          icon = R.drawable.ic_close,
          contentDescription = "Cancel",
          colors = IconButtonDefaults.filledIconButtonColors(contentColor = Color.Red),
          onClick = action
        ),
        rightButtonData = ButtonData(
          icon = R.drawable.ic_check,
          contentDescription = "Confirm"
        ) {
//          creatingPoi.image = ""
          viewModel.addPoi(creatingPoi)
          action()
        }
      )
    }
  ) {
    PoiDetailContent(creatingPoi, { creatingPoi = it }, false)
  }
}
