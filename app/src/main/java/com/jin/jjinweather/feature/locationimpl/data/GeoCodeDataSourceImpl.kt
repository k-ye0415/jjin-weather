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

    override suspend fun findCityNameAt(latitude: Double, longitude: Double): Result<String> =
        withContext(Dispatchers.IO) {
            try {
                val geocoder = Geocoder(context, Locale.getDefault())
                val address = geocoder.getFromLocation(latitude, longitude, 1)?.firstOrNull()
                if (address == null) {
                    Result.failure(IOException(context.getString(R.string.error_unknown_address)))
                } else {
                    val cityName = "${address.adminArea.orEmpty()} ${address.subLocality.orEmpty()}".trim()
                    Result.success(cityName)
                }
            } catch (e: IOException) {
                Result.failure(IOException(context.getString(R.string.error_unknown_address)))
            }
        }
}
