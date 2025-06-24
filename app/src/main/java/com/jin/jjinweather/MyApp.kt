package com.jin.jjinweather

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.jin.jjinweather.feature.network.TranslateApiClient
import java.util.Locale

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Places.initialize(applicationContext, BuildConfig.GOOGLE_PLACES_API_KEY, Locale.KOREA)
        TranslateApiClient.initialize(applicationContext)
    }
}
