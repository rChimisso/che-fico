package com.kreinto.chefico.managers

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager

sealed class Language(val locale: LocaleListCompat) {
  object Italian : Language(LocaleListCompat.forLanguageTags("it"))
  object English : Language(LocaleListCompat.forLanguageTags("en"))
}

class Theme {
  companion object {
    const val DARK = MODE_NIGHT_YES
    const val LIGHT = MODE_NIGHT_NO
    const val SYSTEM = MODE_NIGHT_FOLLOW_SYSTEM
  }
}

class SettingsManager(context: Context) {
  private val languageSetting = "language"
  private val autoDeleteNotificationsSetting = "autoDeleteNotifications"
  private val themeModeSetting = "themeMode"

  private val settings: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  fun getLanguage(): String {
    return settings.getString(languageSetting, Language.Italian.locale.toLanguageTags())!!
  }

  fun setLanguage(language: Language) {
    settings.edit().putString(languageSetting, language.locale.toLanguageTags()).apply()
    setApplicationLocales(language.locale)
  }

  fun getAutoDeleteNotification(): Boolean {
    return settings.getBoolean(autoDeleteNotificationsSetting, false)
  }

  fun setAutoDeleteNotification(value: Boolean) {
    settings.edit().putBoolean(autoDeleteNotificationsSetting, value).apply()
  }

  fun getTheme(): Int {
    return settings.getInt(themeModeSetting, Theme.SYSTEM)
  }

  fun applyTheme(theme: Int = getTheme()) {
    settings.edit().putInt(themeModeSetting, theme).apply()
    setDefaultNightMode(theme)
  }
}
