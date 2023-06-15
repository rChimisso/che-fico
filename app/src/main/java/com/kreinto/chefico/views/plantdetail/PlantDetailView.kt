package com.kreinto.chefico.views.plantdetail

import android.graphics.BitmapFactory
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.components.misc.Loader
import com.kreinto.chefico.fixOrientation
import com.kreinto.chefico.room.entities.Poi
import com.kreinto.chefico.room.viewmodels.LocalViewModel
import com.kreinto.chefico.ui.theme.*
import com.kreinto.chefico.views.camera.PlantRecognition
import java.net.URLDecoder

@ExperimentalMaterial3Api
@ExperimentalMaterialApi
@Composable
fun PlantDetailView(onNavigate: (String) -> Unit, imageURI: String?, organ: String?, viewModel: LocalViewModel) {
  val inputStream = LocalContext.current.contentResolver.openInputStream(Uri.parse(URLDecoder.decode(imageURI, "utf-8")))
  val image = BitmapFactory.decodeStream(inputStream)
  val result = remember { mutableStateOf(PlantRecognition.InvalidData) }
  val description = remember { mutableStateOf("") }
  val wikipediaUrl = remember { mutableStateOf("") }
  LaunchedEffect(Unit) {
    PlantRecognition.recognize(image, organ ?: PlantRecognition.PlantOrgan.leaf) {
      result.value = it
      PlantRecognition.fetchPlantDescription(
        result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: ""
      ) { url, data ->
        wikipediaUrl.value = url
        description.value = data.getOrNull(0)?.extract ?: ""
      }
    }
  }

  SimpleFrame(
    onNavigate,
    bottomBar = {
      Column(
        modifier = Modifier
          .background(
            MaterialTheme.colorScheme.background,
            MaterialTheme.shapes.medium
          )
      ) {
        Row(
          modifier = Modifier
            .padding(PaddingLarge)
            .fillMaxWidth()
            .height(InteractSizeMedium),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          val name = result.value.results?.getOrNull(0)?.species?.commonNames?.getOrNull(0) ?: stringResource(id = R.string.plant_unknown)
          Text(name, fontSize = LabelExtraLarge)
          TransparentButton(R.drawable.ic_arrow_next, R.string.save, iconColor = MaterialTheme.colorScheme.primary) {
            viewModel.setCreatingPoi(Poi(name, description.value, imageURI!!))
            onNavigate(CheFicoRoute.PoiCreation.path)
          }
        }
        Column {
          val commonNames = result.value.results?.getOrNull(0)?.species?.commonNames
          if (commonNames != null && commonNames.isNotEmpty()) {
            Row(Modifier.padding(PaddingLarge), horizontalArrangement = Arrangement.spacedBy(PaddingMedium)) {
              Text(stringResource(R.string.plant_aka), fontSize = LabelLarge)
              Column(verticalArrangement = Arrangement.spacedBy(PaddingMedium)) {
                commonNames.forEach { name ->
                  Text(name, fontSize = LabelLarge)
                }
              }
            }
          }
          if (description.value.isNotBlank()) {
            val uriHandler = LocalUriHandler.current
            val annotatedString = buildAnnotatedString {
              addStringAnnotation(
                tag = "URL",
                annotation = wikipediaUrl.value,
                start = 0,
                end = wikipediaUrl.value.length
              )
            }
            Spacer(Modifier.height(PaddingLarge))
            ClickableText(
              text = annotatedString,
              onClick = {
                annotatedString
                  .getStringAnnotations("URL", it, it)
                  .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                  }
              }
            )
            Spacer(Modifier.height(PaddingLarge))
            Text(description.value, Modifier.padding(PaddingLarge))
          }
        }
      }
    }
  ) {
    Image(
      bitmap = image.fixOrientation().asImageBitmap(),
      contentDescription = null,
      contentScale = ContentScale.Crop,
      modifier = Modifier.fillMaxSize()
    )
  }
  Loader(!result.value.isValid())
}
