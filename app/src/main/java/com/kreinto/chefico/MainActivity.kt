package com.kreinto.chefico

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.kreinto.chefico.views.dashboard.DashboardView

@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DashboardView()
    }
  }
}


