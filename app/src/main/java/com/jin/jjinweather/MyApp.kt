package com.jin.jjinweather

import android.app.Application
import com.jin.jjinweather.layer.data.database.DatabaseProvider

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        DatabaseProvider.getDatabase(this)
    }
}
