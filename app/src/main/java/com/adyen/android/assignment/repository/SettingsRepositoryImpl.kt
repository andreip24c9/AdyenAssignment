package com.adyen.android.assignment.repository

import android.content.SharedPreferences

class SettingsRepositoryImpl(
    val sharedPreferences: SharedPreferences
) : SettingsRepository {

    private fun putString(key: String, value: String) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    private fun getString(key: String, defaultValue: String? = null): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    companion object {
        private const val PLANETARY_LIST_SORTING_KEY = "planetary_list_sorting_key"
    }

    override fun setPlanetarySorting(value: String) {
        putString(PLANETARY_LIST_SORTING_KEY, value)
    }

    override fun getPlanetarySorting(): String? {
        return getString(PLANETARY_LIST_SORTING_KEY)
    }
}