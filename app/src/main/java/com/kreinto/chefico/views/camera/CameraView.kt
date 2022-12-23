package com.kreinto.chefico.views.camera

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@SuppressLint("RememberReturnType")
@ExperimentalGetImage
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
  val show = remember { mutableStateOf(false) }
  val text = remember {
    mutableStateOf("")
  }
  SimpleFrame(onClick = { onNavigate(AppRoute.Dashboard.route) }) {
    if (show.value) {
      Surface {
        Text(text = text.value)
      }
    } else {
      Box {
        AndroidView({ previewView }, modifier = Modifier.fillMaxSize()) {}
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
                PlantRecognition.recognize(it) { result ->
                  result.get("bestMatch").let {
                    text.value = it.toString()
                    show.value = true
                  }
                }

              }
            }
          }) {
          Icon(imageVector = Icons.Default.Home, contentDescription = "")
        }
      }
    }
  }
}

suspend fun ImageCapture.takePicture(executor: Executor): File {
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

suspend fun Context.getCameraProvider(): ProcessCameraProvider = suspendCoroutine {
  ProcessCameraProvider.getInstance(this).also { future ->
    future.addListener({
      it.resume(future.get())
    }, executor)
  }
}

val Context.executor: Executor get() = ContextCompat.getMainExecutor(this)