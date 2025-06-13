package com.jin.jjinweather.feature.location.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 사용자의 위치 정보
 *
 * @property pageNumber
 * - 기본값은 0 이며, 사용자의 현재 위치 정보를 나타냅니다.
 * - 이후 사용자가 위치를 지정하면, 지정된 위치마다 고유한 페이지 번호가 부여됩니다.
 * - 예: 현재 위치(서울): weatherPage = 0, 지정 위치(도쿄): weatherPage = 1 ...
 * @property latitude 위도
 * @property longitude 경도
 */
@Entity(tableName = "geoPoint", primaryKeys = ["pageNumber"])
data class GeoPointEntity(
    val pageNumber: Int,
    val latitude: Double,
    val longitude: Double
)
