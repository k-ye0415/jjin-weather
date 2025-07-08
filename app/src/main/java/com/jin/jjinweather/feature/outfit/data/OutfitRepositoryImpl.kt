package com.jin.jjinweather.feature.outfit.data

import com.jin.jjinweather.feature.file.FileDataSource
import com.jin.jjinweather.feature.firebase.FirebaseDataSource
import com.jin.jjinweather.feature.outfit.domain.OutfitRepository

class OutfitRepositoryImpl(
    private val openAiDataSource: OpenAiDataSource,
    private val dalleDataSource: DalleDataSource,
    private val fileDataSource: FileDataSource,
    private val firebaseDataSource: FirebaseDataSource,
) : OutfitRepository {

    override suspend fun fetchImagesByTemperature(temperature: Int, feelsLikeTemperature: Int): Result<List<String>> {
        return generateImageUrl(temperature, feelsLikeTemperature).fold(
            onSuccess = {
                val result = it.trim().split(",")
                println("YEJIN result : $result")
                Result.success(result)
            },
            onFailure = { Result.failure(it) }
        )
//        val imageUrls = firebaseDataSource.fetchImagesByTemperature(temperature)
//        return if (imageUrls.size < 2) {
//            generateImageUrl(temperature, feelsLikeTemperature).fold(
//                onSuccess = { Result.success(listOf(it)) },
//                onFailure = { Result.failure(it) }
//            )
//        } else {
//            Result.success(imageUrls.take(2))
//        }
    }

    private suspend fun generateImageUrl(temperature: Int, feelsLikeTemperature: Int): Result<String> {
        return openAiDataSource.generateImagePrompt(temperature, feelsLikeTemperature).fold(
            onSuccess = {
//                val imageUrl = dalleDataSource.requestOutfitImageGeneration(it)
//                val file = fileDataSource.downloadImageUrlToFile(imageUrl.getOrNull().orEmpty())
//                val downloadUrl = firebaseDataSource.uploadAndFetchDownloadUrl(file)
//                firebaseDataSource.uploadFireStore(temperature, downloadUrl)
//                file.delete()
                Result.success(it)
            },
            onFailure = { Result.failure(it) }
        )
    }

    private companion object {
        val TAG = "OutfitRepository"
    }
}
