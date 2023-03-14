package com.kreinto.chefico.components.util

//import androidx.compose.material3.NavigationRailDefaults.ContainerColor
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.toggleable
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.unit.Dp
@ExperimentalMaterialApi
@Composable
fun ModalBottomSheet(
  onDismissRequest: () -> Unit,
//  modifier: Modifier = Modifier,
//  sheetState: ModalBottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden),
//  shape: Shape = ModalBottomSheetDefaults.ExpandedShape,
//  containerColor: Color = ModalBottomSheetDefaults.ContainerColor,
//  contentColor: Color = contentColorFor(contentColorFor(backgroundColor = Che),
//  tonalElevation: Dp = ModalBottomSheetDefaults.Elevation,
//  scrimColor: Color = ModalBottomSheetDefaults.ScrimColor,
  content: @Composable ColumnScope.() -> Unit
) {
  var openBottomSheet by rememberSaveable { mutableStateOf(false) }
  var skipPartiallyExpanded by remember { mutableStateOf(false) }
  val scope = rememberCoroutineScope()
  val bottomSheetState = rememberModalBottomSheetState(initialValue = ModalBottomSheetValue.Hidden)

// App content
  Column(
    modifier = Modifier.fillMaxSize(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.Center
  ) {
    Row(
      Modifier.toggleable(
        value = skipPartiallyExpanded,
        role = Role.Checkbox,
        onValueChange = { checked -> skipPartiallyExpanded = checked }
      )
    ) {
      Checkbox(checked = skipPartiallyExpanded, onCheckedChange = null)
      Spacer(Modifier.width(16.dp))
      Text("Skip partially expanded State")
    }
    Button(onClick = { openBottomSheet = !openBottomSheet }) {
      Text(text = "Show Bottom Sheet")
    }
  }

// Sheet content
  if (openBottomSheet) {
    ModalBottomSheet(
      onDismissRequest = { openBottomSheet = false },
    ) {
      Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
          // Note: If you provide logic outside of onDismissRequest to remove the sheet,
          // you must additionally handle intended state cleanup, if any.
          onClick = {
            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
              if (!bottomSheetState.isVisible) {
                openBottomSheet = false
              }
            }
          }
        ) {
          Text("Hide Bottom Sheet")
        }
      }
    }
  }
}