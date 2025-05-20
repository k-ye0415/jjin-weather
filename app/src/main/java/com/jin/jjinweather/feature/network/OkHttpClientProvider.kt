package com.jin.jjinweather.feature.network

import okhttp3.OkHttpClient

object OkHttpClientProvider {
    val baseHttpClient: OkHttpClient = OkHttpClient.Builder().build()
}
