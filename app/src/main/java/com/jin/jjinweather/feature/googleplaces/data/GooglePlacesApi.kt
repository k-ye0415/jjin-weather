package com.jin.jjinweather.feature.googleplaces.data

import com.jin.jjinweather.feature.googleplaces.data.model.PlaceDetailResponse
import com.jin.jjinweather.feature.googleplaces.data.model.PlaceResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GooglePlacesApi {
    @GET("place/autocomplete/json")
    suspend fun queryPlace(
        @Query("input") input: String,
        @Query("types") types: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): PlaceResponse

    @GET("place/details/json")
    suspend fun queryPlaceDetail(
        @Query("place_id") placeId: String,
        @Query("fields") types: String,
        @Query("language") language: String,
        @Query("key") apiKey: String
    ): PlaceDetailResponse
}
