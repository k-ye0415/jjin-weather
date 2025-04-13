package com.jin.jjinweather.layer.domain.repository

interface PreferencesRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
}
