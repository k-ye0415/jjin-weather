package com.jin.jjinweather.feature.firebase

import java.io.File

interface FirebaseDataSource {
    suspend fun uploadAndFetchDownloadUrl(file: File): String
    suspend fun uploadFireStore(temperature: Int, imageUrl: String)
    suspend fun fetchImagesByTemperature(temperature: Int): List<String>
}
