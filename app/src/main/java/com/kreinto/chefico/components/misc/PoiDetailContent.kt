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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
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
import com.kreinto.chefico.ui.theme.*
import java.io.InputStream
import java.net.URLDecoder

/**
 * Fixes the orientation of the given [Bitmap] image.
 *
 * @param source
 * @return
 */
private fun fixOrientation(source: Bitmap): ImageBitmap {
  val matrix = Matrix()
  matrix.postRotate(90f)
  return Bitmap.createBitmap(
    source, 0, 0, source.width, source.height,
    matrix, true
  ).asImageBitmap()
}

/**
 * POI detail.
 *
 * @param poi
 * @param updatePoi
 * @param showActions
 * @param viewModel
 * @param authViewModel
 */
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
  val shareSuccessMessage = stringResource(R.string.share_success)
  val shareFailureMessage = stringResource(R.string.share_failure)

  val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
    if (uri != null) {
      poi.image = uri.toString()
      updatePoi(poi)
    }
  }

  if (openBottomSheet) {
    Dialog({ openBottomSheet = false }) {
      Column(
        verticalArrangement = Arrangement.spacedBy(PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.extraSmall)
          .padding(PaddingLarge),
      ) {
        Text(stringResource(R.string.share), color = MaterialTheme.colorScheme.primary)
        TextInput(
          onValueChange = { user = it },
          singleLine = true,
          label = R.string.user_id,
          leadingIcon = { Icon(painterResource(R.drawable.ic_account), stringResource(R.string.user_id), Modifier.size(IconSizeMedium)) }
        )
        SubmitButton(
          enabled = user.isNotBlank(),
          text = R.string.share
        ) {
          authViewModel.share(user, poi.id) {
            Toast.makeText(context, if (it) shareSuccessMessage else shareFailureMessage, Toast.LENGTH_SHORT).show()
            openBottomSheet = false
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
        shadowElevation = 12.dp, // TODO: Check if it can be removed
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
                  fixOrientation(image),
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
                .offset(y = InteractSizeMedium / 2)
            ) {
              if (showActions) {
                FilledButton(R.drawable.ic_share, R.string.share) { openBottomSheet = true }
                Spacer(Modifier.width(PaddingMedium))
                FilledButton(R.drawable.ic_map, R.string.open_maps) {
                  val intent = Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q=${poi.latitude},${poi.longitude}"))
                  intent.setPackage("com.google.android.apps.maps")
                  context.startActivity(intent)
                }
                Spacer(Modifier.width(PaddingMedium))
              }
              FilledButton(R.drawable.ic_photo_camera, R.string.change_pic) { galleryLauncher.launch("image/*") }
              Spacer(Modifier.width(PaddingMedium))
            }
          }
          TextInput(
            modifier = Modifier.fillMaxWidth(2f / 3f),
            underline = false,
            init = name,
            textStyle = bodyStyle.merge(TextStyle(fontSize = LabelExtraLarge)),
            onFocusChanged = {
              poi.name = name
              updatePoi(poi)
            }
          ) { name = it }
        }
      }
      Surface(
        shadowElevation = 12.dp, // TODO: Check if it can be removed
        modifier = Modifier
          .padding(PaddingLarge)
          .fillMaxWidth(),
        shape = MaterialTheme.shapes.small
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
        var notificationName by remember { mutableStateOf("") }
        var notificationMessage by remember { mutableStateOf("") }
        val dateRangePickerState = rememberDatePickerState()
        DatePickerDialog(
          onDismissRequest = { openNotificationPopUp = false },
          dismissButton = {
            TextButton({ openNotificationPopUp = false }) {
              Text(stringResource(R.string.cancel), fontSize = LabelExtraLarge)
            }
          },
          confirmButton = {
            TextButton(
              onClick = {
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
              },
              enabled = notificationName.isNotBlank() && notificationMessage.isNotBlank() && dateRangePickerState.selectedDateMillis != null,
            ) {
              Text(stringResource(R.string.add), fontSize = LabelExtraLarge)
            }
          }) {
          TextInput(
            modifier = Modifier.padding(horizontal = PaddingLarge),
            label = R.string.title,
          ) { notificationName = it }
          TextInput(
            modifier = Modifier.padding(horizontal = PaddingLarge),
            label = R.string.desc,
          ) { notificationMessage = it }
          DatePicker(dateRangePickerState, title = null, headline = null, showModeToggle = false)
        }
      }
      LazyColumn(
        verticalArrangement = Arrangement.spacedBy(PaddingMedium),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
          .fillMaxWidth()
          .padding(PaddingLarge)
      ) {
        items(notifications.value.size) { index ->
          SwipeableItem(
            icon = R.drawable.ic_alert,
            text = notifications.value[index].text,
            actions = arrayOf({
              TransparentButton(R.drawable.ic_close, R.string.del, MaterialTheme.colorScheme.error) {
                viewModel.deleteNotification(notifications.value[index].id)
              }
            })
          ) {}
        }
        item {
          SubmitButton(R.string.add_alarm, enabled = notifications.value.size < 5) {
            if (notifications.value.size < 5) {
              openNotificationPopUp = true
            }
          }
        }
      }
    }
  }
}
