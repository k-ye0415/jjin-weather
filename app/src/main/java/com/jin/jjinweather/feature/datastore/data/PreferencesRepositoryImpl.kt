package com.jin.jjinweather.feature.datastore.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.jin.jjinweather.feature.datastore.PreferencesRepository
import kotlinx.coroutines.flow.first

// 현재 PreferencesRepositoryImpl 내에서만 사용하는 확장 함수.
// 재사용 시 별도 파일로 분리 필요.
val Context.settingDataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesRepositoryImpl(context: Context) : PreferencesRepository {
    private val context = context.applicationContext

    override suspend fun completeFirstLaunch() {
        context.settingDataStore.edit { preferences ->
            preferences[FIRST_LAUNCH_KEY] = false
        }
    }

    override suspend fun isFirstLaunch(): Boolean {
        val result = context.settingDataStore.data.first()
        return result[FIRST_LAUNCH_KEY] ?: true
    }

    companion object {
        private val FIRST_LAUNCH_KEY = booleanPreferencesKey("firstLaunch")
    }
}
