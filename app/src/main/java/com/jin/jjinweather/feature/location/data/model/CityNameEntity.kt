package com.jin.jjinweather.feature.location.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 사용자 위치 정보에 대한 도시 이름
 *
 * @property id 자동 증가되는 시퀀스 넘버
 * @property locationIndex (TODO TemperatureScreen 정의 후 추가 될 예정)
 * - 기본값은 0 이며, 사용자의 현재 위치 정보를 나타냅니다.
 * - 이후 사용자가 위치를 지정하면, 지정된 위치마다 고유한 페이지 번호가 부여됩니다.
 * - 예: 현재 위치(서울): weatherPage = 0, 지정 위치(도쿄): weatherPage = 1 ...
 * @property cityName 도시 이름
 */
@Entity(tableName = "cityName", primaryKeys = ["pageNumber"])
data class CityNameEntity(
    val pageNumber: Int,
    val cityName: String
)
