package com.jin.jjinweather.feature.googleplaces.data.model

import com.google.gson.annotations.SerializedName

data class PlaceDetailResponse(
    @SerializedName("result") val result: PlaceResult,
    @SerializedName("status") val status: String
)

data class PlaceResult(
    @SerializedName("name") val name: String,
    @SerializedName("formatted_address") val formattedAddress: String,
    @SerializedName("geometry") val geometry: Geometry
)

data class Geometry(
    @SerializedName("location") val location: LatLng
)

data class LatLng(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lng") val lng: Double
)
