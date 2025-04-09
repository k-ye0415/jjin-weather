package com.jin.jjinweather.layer.data.database

import android.util.Log
import com.jin.jjinweather.layer.data.database.dao.GeoPointDAO
import com.jin.jjinweather.layer.data.database.dao.WeatherDAO
import com.jin.jjinweather.layer.data.database.entity.GeoPointEntity
import com.jin.jjinweather.layer.data.weather.toDomainModel
import com.jin.jjinweather.layer.data.weather.toEntityModel
import com.jin.jjinweather.layer.domain.model.location.GeoPoint
import com.jin.jjinweather.layer.domain.model.weather.Weather

class RoomDataSource(private val geoPointDAO: GeoPointDAO, private val weatherDAO: WeatherDAO) {

    suspend fun insertGeoPointToLocalDB(latitude: Double, longitude: Double) {
        try {
            geoPointDAO.saveGeoPointToLocalDB(GeoPointEntity(latitude = latitude, longitude = longitude))
        } catch (e: Exception) {
            Log.w(TAG, "Database 에 위치 정보 저장 실패: ${e.message}")
        }
    }

    suspend fun fetchLastGeoPointFromLocalDB(): GeoPoint {
        val entity = geoPointDAO.loadLastGeoPointFromLocalDB() ?: throw Exception("Database 에서 GeoPoint 를 찾을 수 없습니다.")
        return GeoPoint(entity.latitude, entity.longitude)
    }

    suspend fun insertWeatherToLocalDB(weather: Weather) {
        try {
            weatherDAO.saveWeatherToLocalDB(weather.toEntityModel())
        } catch (e:Exception) {
            Log.w(TAG, "Database 에 날씨 정보 저장 실패: ${e.message}")
        }
    }

    suspend fun fetchLastWeatherFromLocalDB(): Weather {
        val weather = weatherDAO.fetchLastWeatherFromLocalDB() ?: throw Exception("Database 에서 Weather 를 찾을 수 없습니다.")
        return weather.toDomainModel()
    }

    companion object {
        private const val TAG = "RoomDataSource"
    }
}
