package com.jin.jjinweather

import android.app.Application
import com.jin.jjinweather.feature.database.data.DatabaseProvider

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.getDatabase(this)
    }
}
