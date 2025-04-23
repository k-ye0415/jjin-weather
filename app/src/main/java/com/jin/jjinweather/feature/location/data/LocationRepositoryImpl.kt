package com.jin.jjinweather.feature.location.data

import android.database.SQLException
import com.jin.jjinweather.feature.location.GeoPoint
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.location.data.model.CityNameEntity
import com.jin.jjinweather.feature.location.data.model.GeoPointEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val geoPointTrackingDataSource: GeoPointTrackingDataSource,
    private val cityNameTrackingDataSource: CityNameTrackingDataSource,
    private val geoPointDataSource: GeoPointDataSource,
    private val geoCodeDataSource: GeoCodeDataSource,
) : LocationRepository {
    override suspend fun currentGeoPoint(): GeoPoint =
        geoPointDataSource.currentGeoPoint()
            .onSuccess { keepTrackLocationChanges(it) }
            .map { GeoPoint(it.latitude, it.longitude) }
            .getOrElse {
                val (_, latitude, longitude) = queryLatestLocation()
                    ?: return GeoPoint(DEFAULT_LAT, DEFAULT_LNG)
                return GeoPoint(latitude, longitude)
            }

    override suspend fun findCityNameAt(location: GeoPoint): String =
        geoCodeDataSource.findCityNameAt(location.latitude, location.longitude)
            .onSuccess { keepTrackCityNameChanges(it) }
            .map { it }
            .getOrElse { it.message.orEmpty() }

    private suspend fun queryLatestLocation(): GeoPointEntity? =
        try {
            withContext(Dispatchers.IO) {
                geoPointTrackingDataSource.latestGeoPointOrNull()
            }
        } catch (_: SQLException) {
            null
        }

    private suspend fun keepTrackLocationChanges(latestLocation: GeoPoint) {
        try {
            withContext(Dispatchers.IO) {
                geoPointTrackingDataSource.markAsLatestLocation(
                    GeoPointEntity(
                        latitude = latestLocation.latitude, longitude = latestLocation.longitude
                    )
                )
            }
        } catch (_: SQLException) {
            // Silently ignore the error.
        }
    }

    private suspend fun keepTrackCityNameChanges(cityName: String) {
        try {
            withContext(Dispatchers.IO) {
                cityNameTrackingDataSource.markAsLatestCityName(
                    CityNameEntity(cityName = cityName)
                )
            }
        } catch (_: SQLException) {
            // Silently ignore the error.
        }
    }

    private companion object {
        const val DEFAULT_LAT = 37.5
        const val DEFAULT_LNG = 126.9
    }
}
