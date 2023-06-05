package com.kreinto.chefico.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager
import java.util.*

/**
 * Languages.
 */
sealed class Language {
  companion object {
    const val ITALIAN = "it"
    const val ENGLISH = "en"
    val NAME = mapOf(
      ITALIAN to "Italiano",
      ENGLISH to "English"
    )
  }
}

/**
 * Themes.
 */
sealed class Theme {
  companion object {
    const val DARK = MODE_NIGHT_YES
    const val LIGHT = MODE_NIGHT_NO
    const val SYSTEM = MODE_NIGHT_FOLLOW_SYSTEM
  }
}

/**
 * Settings Manager.
 *
 * @param context
 */
class SettingsManager(context: Context) {
  /**
   * Language setting key.
   */
  private val languageKey = "language"

  /**
   * Auto delete notifications setting key.
   */
  private val autoDeleteNotificationsKey = "autoDeleteNotifications"

  /**
   * Theme setting key.
   */
  private val themeKey = "theme"

  /**
   * Last update time key.
   */
  private val lastUpdateKey = "lastUpdate"

  /**
   * Application settings.
   */
  private val settings: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  /**
   * Languaage setting.
   */
  var language: String
    get() {
      return settings.getString(languageKey, Language.ITALIAN)!!
    }
    set(value) {
      settings.edit().putString(languageKey, value).apply()
      setApplicationLocales(LocaleListCompat.forLanguageTags(value))
    }

  /**
   * Auto delete notifications setting.
   */
  var autoDeleteNotifications: Boolean
    get() {
      return settings.getBoolean(autoDeleteNotificationsKey, false)
    }
    set(value) {
      settings.edit().putBoolean(autoDeleteNotificationsKey, value).apply()
    }

  /**
   * Theme setting.
   */
  var theme: Int
    get() {
      return settings.getInt(themeKey, Theme.SYSTEM)
    }
    set(value) {
      settings.edit().putInt(themeKey, value).apply()
      setDefaultNightMode(value)
    }

  /**
   * Last update setting.
   */
  var lastUpdate: Long
    get() {
      return settings.getLong(lastUpdateKey, Date(0).time / 1000)
    }
    set(value) {
      settings.edit().putLong(lastUpdateKey, value).apply()
    }

  /**
   * Refreshes the current theme.
   */
  fun refreshTheme() {
    theme = theme
  }
}
