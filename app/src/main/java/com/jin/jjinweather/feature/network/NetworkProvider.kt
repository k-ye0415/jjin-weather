package com.jin.jjinweather.feature.network

import com.google.gson.Gson
import okhttp3.OkHttpClient

object NetworkProvider {
    val gson = Gson()
    val baseOkHttpClient: OkHttpClient = OkHttpClient.Builder().build()
}
