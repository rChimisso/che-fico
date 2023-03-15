package com.kreinto.chefico.views.camera

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import androidx.camera.core.*
import androidx.camera.core.ImageCapture.CAPTURE_MODE_MAXIMIZE_QUALITY
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import java.io.File
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalComposeUiApi::class)
@SuppressLint("ClickableViewAccessibility")
@ExperimentalMaterial3Api
@ExperimentalGetImage
@Composable
fun CameraView(onNavigate: (route: String) -> Unit) {
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val cameraProvider = ProcessCameraProvider.getInstance(context).get()

  var preview: Preview = Preview.Builder().build()
  val previewView = remember { PreviewView(context) }
  val imageCapture = remember { ImageCapture.Builder().setCaptureMode(CAPTURE_MODE_MAXIMIZE_QUALITY).build() }
  cameraProvider.unbindAll()

  var camera = cameraProvider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageCapture)


  val listener = object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
    override fun onScale(detector: ScaleGestureDetector): Boolean {
      val currentZoomRatio: Float = camera.cameraInfo.zoomState.value?.zoomRatio ?: 1f
      val delta = detector.scaleFactor
      camera.cameraControl.setZoomRatio(currentZoomRatio * delta)
      return true
    }
  }

  val scaleGestureDetector = ScaleGestureDetector(previewView.context, listener)

  previewView.setOnTouchListener { _, event ->
    scaleGestureDetector.onTouchEvent(event)
    if (event.action == MotionEvent.ACTION_DOWN) {
      val factory = previewView.meteringPointFactory
      val point = factory.createPoint(event.x, event.y)
      val action = FocusMeteringAction.Builder(point, FocusMeteringAction.FLAG_AF)
        .setAutoCancelDuration(5, TimeUnit.SECONDS)
        .build()
      camera.cameraControl.startFocusAndMetering(action)
    }
    true

  }
  preview.setSurfaceProvider(previewView.surfaceProvider)


  var cameraFlashEnabled by remember { mutableStateOf(false) }

  SimpleFrame(
    onBackPressed = onNavigate,
  ) {
    Box {
      AndroidView(
        { previewView },
        modifier = Modifier
          .fillMaxSize()
          .align(Alignment.Center),
        update = { }

      )
      Column(
        modifier = Modifier
          .align(Alignment.BottomCenter)
          .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        var show by remember { mutableStateOf(false) }
        val scale: Float by animateFloatAsState(if (show) 1.2f else 1f)
        Box(
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            colors = ButtonDefaults.buttonColors(
              containerColor = Color.Transparent,
              contentColor = Color.White,
            ),
            border = BorderStroke(4.dp, Color.White),
            shape = CircleShape,
            contentPadding = PaddingValues(0.dp),
            modifier = Modifier
              .scale(scale)
              .size(64.dp)
              .align(Alignment.Center)
              .pointerInteropFilter {
                when (it.action) {
                  MotionEvent.ACTION_DOWN -> {
                    show = true
                    val file = File.createTempFile("image", ".jpg")
                    imageCapture.takePicture(
                      ImageCapture.OutputFileOptions
                        .Builder(file)
                        .build(),
                      context.mainExecutor,
                      object : ImageCapture.OnImageSavedCallback {
                        override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                          onNavigate(Route.PlantDetail.route(file.name))
                        }

                        override fun onError(exception: ImageCaptureException) {
                        }
                      }
                    )
                  }
                  MotionEvent.ACTION_UP -> show = false
                  MotionEvent.ACTION_CANCEL -> show = false
                }
                return@pointerInteropFilter true
              },
            onClick = {},
            content = {

            }
          )
          IconButton(
            onClick = {
              cameraFlashEnabled = !cameraFlashEnabled
              camera.cameraControl.enableTorch(cameraFlashEnabled)
            },
            modifier = Modifier.align(Alignment.CenterEnd)
          ) {
            Crossfade(targetState = cameraFlashEnabled) {
              if (it) {
                Icon(
                  painter = painterResource(id = R.drawable.ic_torch_on),
                  contentDescription = "torch on",
                  modifier = Modifier.size(24.dp),
                  tint = Color.White
                )
              } else {
                Icon(
                  painter = painterResource(id = R.drawable.ic_torch_off),
                  contentDescription = "torch off",
                  modifier = Modifier.size(24.dp),
                  tint = Color.White
                )
              }
            }
          }
        }
        Spacer(modifier = Modifier.height(32.dp))
        SegmentedButton(
          ButtonData(R.drawable.ic_leaf, "Foglia") {},
          ButtonData(R.drawable.ic_flower, "Fiore") {},
          ButtonData(R.drawable.ic_fruit, "Frutto") {},
        )
      }
    }
  }
}

@OptIn(ExperimentalTextApi::class)
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
          .height(40.dp),
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 12.dp,
        color = if (index == selected) MaterialTheme.colorScheme.primary else Color.Transparent,
        contentColor = if (index == selected) MaterialTheme.colorScheme.onPrimary else Color.White,
        onClick = { selected = index }
      ) {
        Row(
          modifier = Modifier.fillMaxSize(),
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.Center
        ) {
          Icon(
            painter = painterResource(id = item.icon),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
          )
          Spacer(modifier = Modifier.width(8.dp))
          Text(
            item.contentDescription,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
          )
        }
      }
    }
  }
}