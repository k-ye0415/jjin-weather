package com.jin.jjinweather.feature.location.data

import android.database.SQLException
import com.jin.jjinweather.feature.location.City
import com.jin.jjinweather.feature.location.GeoPoint
import com.jin.jjinweather.feature.location.LocationRepository
import com.jin.jjinweather.feature.location.data.model.CityNameEntity
import com.jin.jjinweather.feature.location.data.model.GeoPointEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class LocationRepositoryImpl(
    private val geoPointTrackingDataSource: GeoPointTrackingDataSource,
    private val cityNameTrackingDataSource: CityNameTrackingDataSource,
    private val geoPointDataSource: GeoPointDataSource,
    private val geoCodeDataSource: GeoCodeDataSource,
) : LocationRepository {
    override suspend fun currentGeoPoint(): GeoPoint =
        geoPointDataSource.currentGeoPoint(DEFAULT_PAGE_NUM)
            .onSuccess { keepTrackLocationChanges(it) }
            .map { GeoPoint(DEFAULT_PAGE_NUM, it.latitude, it.longitude) }
            .getOrElse {
                val (pageNum, latitude, longitude) = queryLatestLocation(DEFAULT_PAGE_NUM)
                    ?: return GeoPoint(DEFAULT_PAGE_NUM, DEFAULT_LAT, DEFAULT_LNG)
                return GeoPoint(pageNum, latitude, longitude)
            }

    override suspend fun findCityNameAt(location: GeoPoint): String =
        geoCodeDataSource.findCityNameAt(location.latitude, location.longitude)
            .onSuccess { keepTrackCityNameChanges(City(DEFAULT_PAGE_NUM, it)) }
            .map { it }
            .getOrElse { it.message.orEmpty() }

    override suspend fun insertCityName(city: City) {
        try {
            withContext(Dispatchers.IO) {
                keepTrackCityNameChanges(city)
            }
        } catch (e: Exception) {
            //
        }
    }

    override suspend fun insertGeoPoint(geoPoint: GeoPoint) {
        try {
            withContext(Dispatchers.IO) {
                keepTrackLocationChanges(geoPoint)
            }
        } catch (e: Exception) {
            //
        }
    }

    override fun fetchGeoPoints(): Flow<List<GeoPoint>> {
        return geoPointTrackingDataSource.observeGeoPoints()
            .map { entities ->
                entities.map { entity ->
                    GeoPoint(
                        pageNumber = entity.pageNumber,
                        latitude = entity.latitude,
                        longitude = entity.longitude
                    )
                }
            }
    }

    override fun fetchCityNames(): Flow<List<City>> {
        return cityNameTrackingDataSource.observeCityNames()
            .map { entities ->
                entities.map {
                    City(it.pageNumber, it.cityName)
                }
            }
    }

    private suspend fun queryLatestLocation(pageNumber: Int): GeoPointEntity? =
        try {
            withContext(Dispatchers.IO) {
                geoPointTrackingDataSource.latestGeoPointOrNull(pageNumber)
            }
        } catch (_: SQLException) {
            null
        }

    private suspend fun keepTrackLocationChanges(latestLocation: GeoPoint) {
        try {
            withContext(Dispatchers.IO) {
                geoPointTrackingDataSource.markAsLatestLocation(
                    GeoPointEntity(
                        pageNumber = latestLocation.pageNumber ?: 0,
                        latitude = latestLocation.latitude, longitude = latestLocation.longitude
                    )
                )
            }
        } catch (_: SQLException) {
            // Silently ignore the error.
        }
    }

    private suspend fun keepTrackCityNameChanges(city: City) {
        try {
            withContext(Dispatchers.IO) {
                cityNameTrackingDataSource.markAsLatestCityName(
                    CityNameEntity(pageNumber = city.pageNumber, cityName = city.name)
                )
            }
        } catch (_: SQLException) {
            // Silently ignore the error.
        }
    }

    private companion object {
        const val DEFAULT_LAT = 37.5
        const val DEFAULT_LNG = 126.9
        const val DEFAULT_PAGE_NUM = 0
    }
}
