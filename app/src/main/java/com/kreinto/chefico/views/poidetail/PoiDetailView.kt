package com.kreinto.chefico.views.poidetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Poi
import java.net.URLDecoder

private fun fixOrientaton(source: Bitmap): ImageBitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  ).asImageBitmap()
}

@ExperimentalMaterial3Api
@Composable
fun PoiDetailView(
  onNavigate: (String) -> Unit,
  viewModel: CheFicoViewModel,
  poiId: String?,
  authViewModel: AuthViewModel
) {
  var poi by remember {
    mutableStateOf(Poi.NullPoi)
  }
  var user by remember {
    mutableStateOf("VJoSORl1KNP1n7Cs6Alu2qnJg7E3")
  }
  val context = LocalContext.current
  var openBottomSheet by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState()

  LaunchedEffect(poiId) {
    if (poiId != null) {
      viewModel.getPoi(poiId.toInt()).collect {
        poi = it
      }
    }
  }

  val showActions: Boolean = true
  SimpleFrame(onBackPressed = onNavigate) {

    if (openBottomSheet) {
      ModalBottomSheet(onDismissRequest = { openBottomSheet = false }) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.Center,
          verticalAlignment = Alignment.CenterVertically
        ) {
          TextField(
            modifier = Modifier.padding(12.dp), value = user, onValueChange = { user = it }, singleLine = true,
            colors = TextFieldDefaults.colors(
              unfocusedTextColor = Color(0xff32C896),
              focusedContainerColor = Color.Transparent,
              unfocusedContainerColor = Color.Transparent,
              disabledContainerColor = Color.Transparent,
              cursorColor = Color(0xff32C896),
              focusedIndicatorColor = Color(0x6632C896),
              unfocusedIndicatorColor = Color(0x6632C896),
              disabledIndicatorColor = Color(0x6632C896),
              focusedLeadingIconColor = Color(0xff32C896),
              unfocusedLeadingIconColor = Color(0xff32C896),
              focusedTrailingIconColor = Color(0xff32C896),
              unfocusedTrailingIconColor = Color(0x6632C896),
              focusedLabelColor = Color.Transparent,
              unfocusedLabelColor = Color(0xff32C896),
              disabledLabelColor = Color(0xff32C896),
              unfocusedPlaceholderColor = Color(0xff32C896),
            ),
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = "account",
                modifier = Modifier.size(24.dp)
              )
            }
          )
        }
        TextButton(
          enabled = user.isNotEmpty(),
          colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
          ),
          contentPadding = PaddingValues(0.dp),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .width(208.dp)
            .height(40.dp),
          onClick = {
            authViewModel.share(user, poi)
          }
        ) {
          Box(
            modifier = Modifier
              .fillMaxSize()
              .clip(RoundedCornerShape(12.dp))
              .background(Brush.verticalGradient(listOf(Color(0xff32C896), Color(0x6632C896))))
          ) {
            Text(text = "Condividi", fontSize = 16.sp, modifier = Modifier.align(Alignment.Center))
          }
        }
      }
    }

    Column {
      if (poi != Poi.NullPoi) {
        val stream = context.contentResolver.openInputStream(Uri.parse(URLDecoder.decode(poi.image, "utf-8")))
        val image = BitmapFactory.decodeStream(stream)
        var name by rememberSaveable { mutableStateOf(poi.name) }
        var description by rememberSaveable { mutableStateOf(poi.description) }
        Surface(
          shadowElevation = 12.dp,
          modifier = Modifier.fillMaxWidth(),
        ) {
          Column {
            Box {
              Box(
                modifier = Modifier
                  .fillMaxWidth()
                  .height(160.dp)
                  .align(Alignment.TopCenter),
              ) {
                if (image != null) {
                  Image(
                    bitmap = fixOrientaton(image),
                    null,
                    contentScale = ContentScale.Crop
                  )
                } else {
                  Image(
                    painterResource(R.drawable.no_image),
                    null,
                    contentScale = ContentScale.Crop
                  )
                }
              }
              Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier
                  .align(Alignment.BottomEnd)
                  .fillMaxWidth()
                  .offset(y = 20.dp)
              ) {
                if (showActions) {
                  FilledButton(icon = R.drawable.ic_share, contentDescription = "Share") {
                    openBottomSheet = true
                  }
                  Spacer(modifier = Modifier.width(8.dp))
                }
                FilledButton(icon = R.drawable.ic_close, contentDescription = "Change image") {
                  // TODO: Change image (pick from gallery and overwrite)
                }
                Spacer(modifier = Modifier.width(8.dp))
              }
            }
            TextInput(
              modifier = Modifier.fillMaxWidth(2f / 3f),
              init = name,
              textColor = Color(0xff4caf50),
              textStyle = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
              ),
              onFocusChanged = {
                poi.name = name
                viewModel.updatePoi(poi)
              },
              onValueChange = { name = it }
            )
          }
        }
        Surface(
          shadowElevation = 12.dp,
          modifier = Modifier.padding(16.dp),
          shape = RoundedCornerShape(10.dp)
        ) {
          Column {
            TextInput(
              modifier = Modifier.requiredHeight(128.dp),
              init = description,
              textStyle = TextStyle(fontSize = 18.sp),
              singleLine = false,
              onFocusChanged = {
                poi.description = description
                viewModel.updatePoi(poi)
              },
              onValueChange = { description = it }
            )
            Divider(modifier = Modifier.height(2.dp))
          }
        }
      }
    }
  }
}
