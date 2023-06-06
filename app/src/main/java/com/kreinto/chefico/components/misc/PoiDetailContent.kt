package com.kreinto.chefico.components.misc

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.widget.Toast
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.buttons.SubmitButton
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.inputs.TextInput
import com.kreinto.chefico.components.items.SwipeableItem
import com.kreinto.chefico.managers.PoiNotificationManager
import com.kreinto.chefico.room.entities.Notification
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.viewmodels.AuthViewModel
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import java.io.InputStream
import java.net.URLDecoder

private fun fixOrientaton(source: Bitmap): ImageBitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  ).asImageBitmap()
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@Composable
fun PoiDetailContent(poi: Poi, updatePoi: (Poi) -> Unit, showActions: Boolean, viewModel: LocalViewModel, authViewModel: AuthViewModel) {
  val context = LocalContext.current
  var user by remember { mutableStateOf("") }
  var openBottomSheet by remember { mutableStateOf(false) }
  val notifications = viewModel.getPoiNotifications(poi.id).collectAsStateWithLifecycle(emptyList())
  var openNotificationPopUp by remember { mutableStateOf(false) }

  val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    if (uri != null) {
      poi.image = uri.toString()
      updatePoi(poi)
    }
  }

  if (openBottomSheet) {
    Dialog({ openBottomSheet = false }) {
      Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(16.dp))
          .padding(16.dp),
      ) {
        Text("Condividi", color = MaterialTheme.colorScheme.primary)
        TextInput(
          onValueChange = { user = it },
          singleLine = true,
          label = "ID utente",
          leadingIcon = { Icon(painterResource(id = R.drawable.ic_account), "account", Modifier.size(24.dp)) }
        )
        SubmitButton(
          enabled = user.isNotBlank(),
          text = "Condividi"
        ) {
          authViewModel.share(user, poi.id) {
            if (it) {
              Toast.makeText(context, "Condivisione riuscita", Toast.LENGTH_SHORT).show()
            } else {
              Toast.makeText(context, "Condivisione non riuscita", Toast.LENGTH_SHORT).show()
            }
          }
        }
      }
    }
  }

  Column {
    if (poi != Poi.NullPoi) {
      val stream: InputStream?
      var image: Bitmap? = null
      if (poi.image.isNotBlank()) {
        stream = context.contentResolver.openInputStream(Uri.parse(URLDecoder.decode(poi.image, "utf-8")))
        image = BitmapFactory.decodeStream(stream)
      }
      var name by rememberSaveable { mutableStateOf(poi.name) }
      var description by rememberSaveable { mutableStateOf(poi.description) }
      Surface(
        shadowElevation = 12.dp,
        modifier = Modifier.fillMaxWidth()
      ) {
        Column {
          Box {
            Box(
              Modifier
                .fillMaxWidth()
                .height(160.dp)
                .align(Alignment.TopCenter),
            ) {
              if (image != null) {
                Image(
                  fixOrientaton(image),
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
                FilledButton(R.drawable.ic_share, "Share") { openBottomSheet = true }
                Spacer(Modifier.width(8.dp))
                FilledButton(R.drawable.ic_map, "Open Google Maps") {
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=${poi.latitude},${poi.longitude}"))
                  intent.setPackage("com.google.android.apps.maps")
                  context.startActivity(intent)
                }
                Spacer(Modifier.width(8.dp))
              }
              FilledButton(R.drawable.ic_photo_camera, "Change image") { galleryLauncher.launch("image/*") }
              Spacer(Modifier.width(8.dp))
            }
          }
          TextInput(
            modifier = Modifier.fillMaxWidth(2f / 3f),
            underline = false,
            init = name,
            fontSize = 24.sp,
            onFocusChanged = {
              poi.name = name
              updatePoi(poi)
            }
          ) { name = it }
        }
      }
      Surface(
        shadowElevation = 12.dp,
        modifier = Modifier
          .padding(16.dp)
          .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
      ) {
        TextInput(
          init = description,
          singleLine = false,
          underline = false,
          onValueChange = { description = it },
          onFocusChanged = {
            poi.description = description
            updatePoi(poi)
          }
        )
      }
      if (openNotificationPopUp) {
        Dialog({ openNotificationPopUp = false }) {
          var notificationName by remember { mutableStateOf("") }
          var notificationMessage by remember { mutableStateOf("") }
          val dateRangePickerState = rememberDatePickerState()
          Surface(shape = RoundedCornerShape(12.dp)) {
            Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(16.dp)) {
              TextInput(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                label = "Titolo",
              ) { notificationName = it }
              DatePicker(dateRangePickerState, title = null, headline = null, showModeToggle = false)
              TextInput(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
                label = "Descrizione",
              ) { notificationMessage = it }
              SubmitButton(
                text = "Aggiungi",
                enabled = notificationName.isNotBlank() && notificationMessage.isNotBlank() && dateRangePickerState.selectedDateMillis != null,
              ) {
                openNotificationPopUp = false
                viewModel.addNotification(
                  Notification(
                    icon = "",
                    text = notificationName,
                    message = notificationMessage,
                    poiId = poi.id
                  )
                )
                PoiNotificationManager.scheduleNotification(
                  context,
                  dateRangePickerState.selectedDateMillis!!,
                  notificationName,
                  notificationMessage
                )
              }
            }
          }
        }
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
              TransparentButton(R.drawable.ic_close, "Elimina", MaterialTheme.colorScheme.error) {
                viewModel.deleteNotification(notifications.value[index].id)
              }
            })
          ) {}
        }
        item {
          SubmitButton(text = "Aggiungi notifica", enabled = notifications.value.size < 5) {
            if (notifications.value.size < 5) {
              openNotificationPopUp = true
            }
          }
        }
      }
    }
  }
}
