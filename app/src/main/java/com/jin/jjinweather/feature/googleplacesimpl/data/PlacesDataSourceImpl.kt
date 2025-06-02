package com.jin.jjinweather.feature.googleplacesimpl.data

import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.googleplaces.data.GooglePlacesApi
import com.jin.jjinweather.feature.googleplaces.data.PlacesDataSource
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.GeoPoint

class PlacesDataSourceImpl(private val googlePlacesApi: GooglePlacesApi) : PlacesDataSource {
    override suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>> {
        return try {
            // 자동검색 후 응답받은 결과의 place Id 만 추림
            val placeIds = googlePlacesApi.queryPlaces(
                input = keyword,
                types = PLACE_TYPE,
                language = LANGUAGE,
                apiKey = API_KYE
            ).predictions.map { it.placeId }

            // place id 로 정확한 위치 행정이름과 위도/경도 요청
            val districts = placeIds.map { fetchDistrictDetailByPlaceId(it) }

            val success = districts
                .filter { it.isSuccess }
                .map { it.getOrNull() ?: District(DEFAULT_ADDRESS, GeoPoint(DEFAULT_LAT, DEFAULT_LNG)) }

            if (success.isNotEmpty()) Result.success(success)
            else Result.failure(Exception("No district found"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun fetchDistrictDetailByPlaceId(placeId: String): Result<District> {
        return try {
            val response = googlePlacesApi.queryPlaceDetails(
                placeId = placeId,
                types = PLACE_DETAIL_TYPE,
                language = LANGUAGE,
                apiKey = API_KYE
            )
            val district = District(
                address = response.result.formattedAddress,
                geoPoint = GeoPoint(
                    latitude = response.result.geometry.location.lat,
                    longitude = response.result.geometry.location.lng
                )
            )
            Result.success(district)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private companion object {
        const val PLACE_TYPE = "(regions)"
        const val PLACE_DETAIL_TYPE = "geometry,name,formatted_address"
        const val LANGUAGE = "ko"
        const val API_KYE = BuildConfig.GOOGLE_PLACES_API_KEY
        const val DEFAULT_ADDRESS = "서울시 서초구"
        const val DEFAULT_LAT = 37.5
        const val DEFAULT_LNG = 126.9
    }
}
