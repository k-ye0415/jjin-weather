package com.jin.jjinweather.feature.locationimpl.data

import android.content.Context
import android.location.Geocoder
import com.jin.jjinweather.R
import com.jin.jjinweather.feature.location.data.GeoCodeDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.Locale

class GeoCodeDataSourceImpl(context: Context) : GeoCodeDataSource {
    private val context = context.applicationContext

    override suspend fun findCityNameAt(latitude: Double, longitude: Double): String =
        withContext(Dispatchers.IO) {
            val address = try {
                val geocoder = Geocoder(context, Locale.getDefault())
                geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
            } catch (e: IOException) {
                null
            }

            if (address == null) {
                context.getString(R.string.error_unknown_address)
            } else {
                "${address.adminArea.orEmpty()} ${address.subLocality.orEmpty()}".trim()
            }
        }
}
