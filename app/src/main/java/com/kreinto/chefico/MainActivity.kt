package com.kreinto.chefico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import com.kreinto.chefico.components.AppFrame

class MainActivity : ComponentActivity() {
  @ExperimentalMaterial3Api()
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AppFrame()
    }
  }
}


