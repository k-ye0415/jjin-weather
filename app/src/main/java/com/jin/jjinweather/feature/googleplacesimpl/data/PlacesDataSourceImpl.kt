package com.jin.jjinweather.feature.googleplacesimpl.data

import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.googleplaces.data.GooglePlacesApi
import com.jin.jjinweather.feature.googleplaces.data.PlacesDataSource
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.GeoPoint

class PlacesDataSourceImpl(private val googlePlacesApi: GooglePlacesApi) : PlacesDataSource {
    override suspend fun searchDistrictsByKeyword(keyword: String): Result<List<District>> {
        return try {
            val districtList = mutableListOf<District>()
            val predictionResponse = googlePlacesApi.queryPlaces(
                input = keyword,
                types = PLACE_TYPE,
                language = LANGUAGE,
                apiKey = API_KYE
            ).predictions
            for (prediction in predictionResponse) {
                fetchDistrictDetailByPlaceId(prediction.placeId)
                    .onSuccess {
                        districtList.add(it)
                    }.onFailure {
                        return Result.failure(it)
                    }
            }
            Result.success(districtList)
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
        val PLACE_TYPE = "(regions)"
        val PLACE_DETAIL_TYPE = "geometry,name,formatted_address"
        val LANGUAGE = "ko"
        val API_KYE = BuildConfig.GOOGLE_PLACES_API_KEY
    }
}
