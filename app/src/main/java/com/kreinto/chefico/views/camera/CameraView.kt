package com.kreinto.chefico.views.camera

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import com.kreinto.chefico.AppRoute
import com.kreinto.chefico.components.frames.SimpleFrame
import com.kreinto.chefico.plantrecognition.PlantRecognition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.concurrent.Executor
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine


@androidx.annotation.OptIn(androidx.camera.core.ExperimentalGetImage::class)
@RequiresApi(Build.VERSION_CODES.P)
@ExperimentalMaterial3Api
@Composable
fun CameraView(
  onNavigate: (route: String) -> Unit
) {
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
  val coroutineScope = rememberCoroutineScope()
  SimpleFrame(onClick = { onNavigate(AppRoute.Dashboard.route) },
    bottomBar = {

    }) {
    Box {
      AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {

      }

      Button(
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
          containerColor = Color.White,
          contentColor = Color.Green
        ),
        modifier = Modifier
          .padding(bottom = 64.dp)
          .size(80.dp)
          .align(Alignment.BottomCenter),
        onClick = {
          coroutineScope.launch {
            imageCapture.takePicture(context.executor).let {
              PlantRecognition.recognize(it)
              onNavigate(AppRoute.Dashboard.route)
            }
          }
        }) {
        Icon(imageVector = Icons.Default.Home, contentDescription = "")
      }
    }
  }
}

suspend fun ImageCapture.takePicture(executor: Executor): File {
  val photoFile = withContext(Dispatchers.IO) {
    kotlin.runCatching {
      File.createTempFile("image", ".jpg")
    }.getOrElse { ex ->
      Log.e("TakePicture", "Failed to create temporary file", ex)
      File("/dev/null")
    }
  }

  return suspendCoroutine { continuation ->
    val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
    takePicture(outputOptions, executor, object : ImageCapture.OnImageSavedCallback {
      override fun onImageSaved(output: ImageCapture.OutputFileResults) {
        continuation.resume(photoFile)
      }

      override fun onError(ex: ImageCaptureException) {
        Log.e("TakePicture", "Image capture failed", ex)
        continuation.resumeWithException(ex)
      }
    })
  }
}

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine { continuation ->
  ProcessCameraProvider.getInstance(this).also { future ->
    future.addListener({
      continuation.resume(future.get())
    }, executor)
  }
}

val Context.executor: Executor
  get() = ContextCompat.getMainExecutor(this)