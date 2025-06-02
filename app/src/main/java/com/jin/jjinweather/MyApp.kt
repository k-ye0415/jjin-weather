package com.jin.jjinweather

import android.app.Application
import com.google.android.libraries.places.api.Places
import java.util.Locale

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (!Places.isInitialized()) {
            Places.initialize(applicationContext, BuildConfig.GOOGLE_PLACES_API_KEY, Locale.KOREA)
        }
    }
}
