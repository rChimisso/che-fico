package com.kreinto.chefico.views.poidetail

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
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
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.room.AuthViewModel
import com.kreinto.chefico.room.CheFicoViewModel
import com.kreinto.chefico.room.entities.Notification
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
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
    mutableStateOf("")
  }
  val context = LocalContext.current
  var openBottomSheet by remember { mutableStateOf(false) }
  val bottomSheetState = rememberModalBottomSheetState()
  var notifications = viewModel.getPoiNotifications(poiId!!.toInt()).collectAsStateWithLifecycle(initialValue = emptyList())

  LaunchedEffect(poiId) {
    if (poiId != null) {

      viewModel.getPoi(poiId.toInt()).collect {
        poi = it
      }

    }
  }
  val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    if (uri != null) {
      poi.image = uri.toString()
      viewModel.updatePoi(poi)
    }
  }
  val showActions: Boolean = true
  SimpleFrame(onBackPressed = onNavigate) {

    if (openBottomSheet) {
      Dialog(onDismissRequest = { openBottomSheet = false }) {
        Column(
          verticalArrangement = Arrangement.Center,
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp)),
        ) {
          Text("Condividi", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
          TextField(
            modifier = Modifier.padding(16.dp),
            value = user, onValueChange = { user = it }, singleLine = true,
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
            placeholder = { Text("ID utente") },
            enabled = true,
            readOnly = false,
            leadingIcon = {
              Icon(
                painter = painterResource(id = R.drawable.ic_account),
                contentDescription = "account",
                modifier = Modifier.size(24.dp)
              )
            }
          )
          TextButton(
            enabled = user.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .padding(bottom = 16.dp)
              .width(208.dp)
              .height(40.dp),

            onClick = {
              authViewModel.share(user, poi)
            }
          ) {
            Text(text = "Condividi", fontSize = 16.sp)
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
          color = MaterialTheme.colorScheme.surface
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
                FilledButton(icon = R.drawable.ic_photo_camera, contentDescription = "Change image") {
                  galleryLauncher.launch("image/*")
                }
                Spacer(modifier = Modifier.width(8.dp))
              }
            }
            TextInput(
              modifier = Modifier.fillMaxWidth(2f / 3f),
              init = name,
              textColor = MaterialTheme.colorScheme.primary,
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
          TextField(
            value = description,
            textStyle = TextStyle(fontSize = 18.sp),
            onValueChange = { description = it },
            modifier = Modifier
              .height(128.dp)
              .onFocusChanged {
                poi.description = description
                viewModel.updatePoi(poi)
              },

            )
        }
        LazyColumn(
          verticalArrangement = Arrangement.spacedBy(8.dp),
          horizontalAlignment = Alignment.CenterHorizontally,
          modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
        ) {
          items(notifications.value.size) { index ->
            SwipeableItem(
              icon = R.drawable.ic_poi,
              text = notifications.value[index].text,
              actions = arrayOf({
                TransparentButton(
                  ButtonData(
                    icon = R.drawable.ic_close,
                    contentDescription = "Elimina",
                    colors = IconButtonDefaults.iconButtonColors(
                      contentColor = MaterialTheme.colorScheme.error
                    ),
                    onClick = {
                      viewModel.deleteNotification(notifications.value[index].id)
                    }
                  )
                )
              }),
              onClick = {}
            )
          }
          item {
            TextButton(
              enabled = notifications.value.size < 5,
              colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
              ),
              contentPadding = PaddingValues(0.dp),
              shape = RoundedCornerShape(12.dp),
              modifier = Modifier
                .padding(bottom = 16.dp)
                .width(208.dp)
                .height(40.dp),

              onClick = {
                if (notifications.value.size < 5) {
                  viewModel.addNotification(
                    Notification(
                      "",
                      "ciaone",
                      poiId.toInt()
                    )
                  )
                }
              }
            ) {
              Text(text = "Aggiungi notifica", fontSize = 16.sp)
            }
          }
        }
      }
    }
  }
}
