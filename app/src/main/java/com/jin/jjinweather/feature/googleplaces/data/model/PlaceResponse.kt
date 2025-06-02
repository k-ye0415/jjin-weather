package com.jin.jjinweather.feature.googleplaces.data.model

import com.google.gson.annotations.SerializedName

data class PlaceResponse(
    @SerializedName("predictions")
    val predictions: List<Prediction>,
    @SerializedName("status")
    val status: String,
)

data class Prediction(
    @SerializedName("description")
    val description: String,
    @SerializedName("place_id")
    val placeId: String
)
