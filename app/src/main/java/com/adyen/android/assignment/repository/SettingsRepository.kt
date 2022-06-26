package com.adyen.android.assignment.repository

interface SettingsRepository {
    fun setPlanetarySorting(value: String)
    fun getPlanetarySorting() : String?
}