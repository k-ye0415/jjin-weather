package com.jin.jjinweather.feature.network

import com.google.gson.Gson
import okhttp3.OkHttpClient

object OkHttpClientProvider {
    val gson = Gson()
    val baseHttpClient: OkHttpClient = OkHttpClient.Builder().build()
}
