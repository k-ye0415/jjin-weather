package com.jin.jjinweather.feature.googleplacesimpl.data

import com.jin.jjinweather.BuildConfig
import com.jin.jjinweather.feature.googleplaces.data.GooglePlacesApi
import com.jin.jjinweather.feature.googleplaces.data.PlacesDataSource
import com.jin.jjinweather.feature.googleplaces.data.model.Prediction
import com.jin.jjinweather.feature.googleplaces.domain.model.District
import com.jin.jjinweather.feature.location.GeoPoint

class PlacesDataSourceImpl(private val googlePlacesApi: GooglePlacesApi) : PlacesDataSource {
    override suspend fun searchDistrictAt(keyword: String): List<Prediction> {
        return googlePlacesApi.queryPlace(
            input = keyword,
            types = "(regions)",
            language = "ko",
            apiKey = BuildConfig.GOOGLE_PLACES_API_KEY
        ).predictions
    }

    override suspend fun searchDistrictDetailAt(prediction: Prediction): District {
        val response = googlePlacesApi.queryPlaceDetail(
            placeId = prediction.placeId,
            types = "geometry,name,formatted_address",
            language = "ko",
            apiKey = BuildConfig.GOOGLE_PLACES_API_KEY
        )
        return District(
            address = response.result.formattedAddress,
            geoPoint = GeoPoint(
                latitude = response.result.geometry.location.lat,
                longitude = response.result.geometry.location.lng
            )
        )
    }
}
