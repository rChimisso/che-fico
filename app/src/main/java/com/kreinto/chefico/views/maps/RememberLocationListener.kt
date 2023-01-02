package com.kreinto.chefico.views.maps

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import com.google.android.gms.location.LocationListener

/**
 * Remember the value [init].
 *
 * @param init [LocationListener] value to remember.
 */
@Composable
fun rememberLocationListener(init: LocationListener) =
  rememberSaveable(saver = Saver({ 0.toByte() }, { init })) { init }
