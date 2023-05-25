package com.kreinto.chefico

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.preference.PreferenceManager

sealed class Language(val locale: LocaleListCompat) {
  object Italian : Language(LocaleListCompat.forLanguageTags("it"))
  object English : Language(LocaleListCompat.forLanguageTags("en"))
}

class SettingsManager(val context: Context) {

  private val languageSetting: String = "language"

  private val settings: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

  fun getLanguage(): String {
    return settings.getString(languageSetting, Language.Italian.locale.toLanguageTags())!!
  }

  fun setLanguage(language: Language) {
    settings.edit().putString(languageSetting, language.locale.toLanguageTags()).apply()
    AppCompatDelegate.setApplicationLocales(language.locale)
  }
}














