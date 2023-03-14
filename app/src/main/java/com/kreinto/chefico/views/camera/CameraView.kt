package com.kreinto.chefico.views.camera

import android.content.Context
import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.kreinto.chefico.R
import com.kreinto.chefico.Route
import com.kreinto.chefico.components.buttons.FilledButton
import com.kreinto.chefico.components.buttons.data.ButtonData
import com.kreinto.chefico.components.frames.SimpleFrame
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

@ExperimentalMaterial3Api
@Composable
fun CameraView(onNavigate: (route: String) -> Unit) {
  val lensFacing = CameraSelector.LENS_FACING_BACK
  val context = LocalContext.current
  val lifecycleOwner = LocalLifecycleOwner.current
  val preview = Preview.Builder().build()
  val imageCapture = remember { ImageCapture.Builder().build() }
  val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()
  val previewView = remember { PreviewView(context) }

  LaunchedEffect(Unit) {
    val cameraProvider = context.getCameraProvider()
    cameraProvider.unbindAll()
    cameraProvider.bindToLifecycle(
      lifecycleOwner,
      cameraSelector,
      preview,
      imageCapture
    )
    preview.setSurfaceProvider(previewView.surfaceProvider)
  }

  val isLoading = remember { mutableStateOf(false) }
  val coroutineScope = rememberCoroutineScope()
  val fileName = remember { mutableStateOf("") }


  SimpleFrame(
    onBackPressed = onNavigate,
    bottomBar = {
      Row(
        modifier = Modifier
          .height(120.dp)
          .fillMaxWidth()
          .padding(BottomAppBarDefaults.ContentPadding),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
      ) {
        FilledButton(
          ButtonData(
            icon = R.drawable.ic_photo_camera,
            contentDescription = "Take a picture",
            height = 80.dp,
            width = 80.dp,
            enabled = !isLoading.value
          ) {
            coroutineScope.launch {
              imageCapture.takePicture(context.executor).let { fileName.value = it.name }
            }
          }
        )
      }
    }
  ) {
    if (!isLoading.value) {
      AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {}
    }
  }
  if (fileName.value != "") {
    onNavigate(Route.PlantDetail.route(fileName.value))
  }
}


private suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine {
  ProcessCameraProvider.getInstance(this).also { future ->
    future.addListener({
      it.resume(future.get()) // FIXME
    }, executor)
  }
}

private suspend fun ImageCapture.takePicture(executor: Executor): File {
  val photoFile = withContext(Dispatchers.IO) {
    kotlin.runCatching {
      File.createTempFile("image", ".jpg")
    }.getOrElse {
      Log.e("TakePicture", "Failed to create temporary file", it)
      File("/dev/null")
    }
  }

  return suspendCoroutine {
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
      override fun onImageSaved(output: ImageCapture.OutputFileResults) {
        it.resume(photoFile)
      }

      override fun onError(e: ImageCaptureException) {
        Log.e("TakePicture", "Image capture failed", e)
        it.resumeWithException(e)
      }
    })
  }
}

private val Context.executor: Executor get() = ContextCompat.getMainExecutor(this)