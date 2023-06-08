package com.kreinto.chefico.views.camera

import android.net.Uri
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.kreinto.chefico.CheFicoRoute
import com.kreinto.chefico.R
import com.kreinto.chefico.components.buttons.TransparentButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.ui.theme.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.net.URLEncoder
import java.util.concurrent.TimeUnit


@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@androidx.annotation.OptIn(androidx.camera.core.ExperimentalZeroShutterLag::class)
@Composable
fun CameraView(onNavigate: (route: String) -> Unit) {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val cameraProvider = ProcessCameraProvider.getInstance(context).get()
  val preview: Preview = Preview.Builder().build()
  val previewView = remember { PreviewView(context) }
  val jpegQuality = 100
  val imageCapture = remember {
    Builder()
      .setCaptureMode(CAPTURE_MODE_ZERO_SHUTTER_LAG)
      .setJpegQuality(jpegQuality).build()
  }
  cameraProvider.unbindAll()

  val camera = cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)

  val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    override fun onScale(detector: ScaleGestureDetector): Boolean {
      val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1f
      val delta = detector.scaleFactor
      camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
      return true
    }
  }

  val scaleGestureDetector = ScaleGestureDetector(previewView.context, listener)

  previewView.setOnTouchListener { view, event ->
    scaleGestureDetector.onTouchEvent(event)
    if (event.action == MotionEvent.ACTION_DOWN) {
      val action = FocusMeteringAction.Builder(previewView.meteringPointFactory.createPoint(event.x, event.y), FocusMeteringAction.FLAG_AF)
        .setAutoCancelDuration(5, TimeUnit.SECONDS)
        .build()
      camera.cameraControl.startFocusAndMetering(action)
      view.performClick()
    }
    true
  }
  preview.setSurfaceProvider(previewView.surfaceProvider)

  var plantOrgan by remember { mutableStateOf(PlantRecognition.PlantOrgan.leaf) }
  var cameraFlashEnabled by remember { mutableStateOf(false) }

  var uri: Uri? by remember { mutableStateOf(null) }

  val galleryLauncher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) {
    if (it != null) {
      uri = it
    }
  }

  LaunchedEffect(uri) {
    if (uri != null) {
      onNavigate(CheFicoRoute.PlantDetail.path(withContext(Dispatchers.IO) { URLEncoder.encode(uri.toString(), "utf-8") }, plantOrgan))
    }
  }

  SimpleFrame(onNavigate) {
    Box {
      AndroidView(
        { previewView },
        Modifier
          .fillMaxSize()
          .align(Alignment.Center),
        {}
      )
      Column(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(PaddingLarge),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(PaddingExtraLarge)
      ) {
        var show by remember { mutableStateOf(false) }
        val scale: Float by animateFloatAsState(if (show) 1.2f else 1f)
        Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceBetween) {
          TransparentButton(
            R.drawable.gallery,
            width = IconSizeExtraLarge,
            iconColor = MaterialTheme.colorScheme.outline
          ) { galleryLauncher.launch("image/*") }
          Button(
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            border = BorderStroke(PaddingSmall, MaterialTheme.colorScheme.outline),
            shape = CircleShape,
            contentPadding = PaddingValues(PaddingNone),
            modifier = Modifier
              .scale(scale)
              .size(CameraTakePictureButtonSize)
              .pointerInteropFilter {
                when (it.action) {
                  MotionEvent.ACTION_DOWN -> {
                    show = true
                    val file = File.createTempFile("image", ".jpg")
                    imageCapture.takePicture(
                      OutputFileOptions
                        .Builder(file)
                        .build(),
                      context.mainExecutor,
                      object : OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: OutputFileResults) {
                          onNavigate(
                            CheFicoRoute.PlantDetail.path(
                              URLEncoder.encode(outputFileResults.savedUri.toString(), "utf-8"),
                              plantOrgan
                            )
                          )
                        }

                        override fun onError(exception: ImageCaptureException) {}
                      }
                    )
                  }
                  MotionEvent.ACTION_UP -> show = false
                  MotionEvent.ACTION_CANCEL -> show = false
                }
                return@pointerInteropFilter true
              },
            onClick = {},
            content = {}
          )
          TransparentButton(
            icon = if (cameraFlashEnabled) R.drawable.ic_torch_on else R.drawable.ic_torch_off,
            contentDescription = R.string.flash,
            iconColor = MaterialTheme.colorScheme.outline,
            width = IconSizeExtraLarge
          ) {
            cameraFlashEnabled = !cameraFlashEnabled
            camera.cameraControl.enableTorch(cameraFlashEnabled)
          }
        }
        SegmentedButton(
          ButtonData(R.drawable.ic_leaf, R.string.leaf) { plantOrgan = PlantRecognition.PlantOrgan.leaf },
          ButtonData(R.drawable.ic_flower, R.string.flower) { plantOrgan = PlantRecognition.PlantOrgan.flower },
          ButtonData(R.drawable.ic_fruit, R.string.fruit) { plantOrgan = PlantRecognition.PlantOrgan.fruit }
        )
      }
    }
  }
}

@Composable
fun SegmentedButton(vararg content: ButtonData) {
  var selected by remember { mutableStateOf(0) }
  Row(
    horizontalArrangement = Arrangement.Center,
    verticalAlignment = Alignment.CenterVertically
  ) {
    content.forEachIndexed { index, item ->
      Surface(
        modifier = Modifier
          .weight(1f)
          .height(InteractSizeMedium),
        shape = MaterialTheme.shapes.small,
        tonalElevation = 12.dp,
        color = if (index == selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        contentColor = if (index == selected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.outline,
        onClick = {
          selected = index
          content[index].onClick()
        }
      ) {
        Row(
          modifier = Modifier.fillMaxSize(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(PaddingSmall)
          ) {
            Icon(painterResource(item.icon), null, Modifier.size(24.dp))
            Text(
              if (item.contentDescription != null) stringResource(id = item.contentDescription) else "",
              fontSize = LabelLarge,
              fontWeight = FontWeight.SemiBold
            )
          }
        }
      }
    }
  }
}