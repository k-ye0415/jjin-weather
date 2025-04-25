package com.jin.jjinweather.feature.datastore

interface PreferencesRepository {
    suspend fun completeFirstLaunch()
    suspend fun isFirstLaunch(): Boolean
}
