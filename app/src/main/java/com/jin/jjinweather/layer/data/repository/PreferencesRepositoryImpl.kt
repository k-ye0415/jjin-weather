package com.jin.jjinweather.layer.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.jin.jjinweather.layer.data.datastore.dataStore
import com.jin.jjinweather.layer.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.first

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val context = context.applicationContext

    override suspend fun completeFirstLaunch() {
        context.dataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }

    override suspend fun isFirstLaunch(): Boolean {
        val result = context.dataStore.data.first()
        return result[FIRST_LAUNCH_KEY] ?: true
    }

    companion object {
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")
    }
}
