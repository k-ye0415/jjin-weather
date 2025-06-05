package com.jin.jjinweather.feature.network

import com.jin.jjinweather.feature.googleplaces.data.GooglePlacesApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GooglePlacesApiClient {
    fun createService(): GooglePlacesApi {
        val client = NetworkProvider.baseOkHttpClient
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/maps/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(NetworkProvider.gson))
            .build()
            .create(GooglePlacesApi::class.java)
    }
}
