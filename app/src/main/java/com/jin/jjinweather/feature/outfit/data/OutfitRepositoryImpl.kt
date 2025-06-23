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

    override suspend fun fetchImagesByTemperature(temperature: Int): Result<List<String>> {
        val imageUrls = firebaseDataSource.fetchImagesByTemperature(temperature)
        return if (imageUrls.size < 2) {
            generateImageUrl(temperature).fold(
                onSuccess = { Result.success(listOf(it)) },
                onFailure = { Result.failure(it) }
            )
        } else {
            Result.success(imageUrls.take(2))
        }
    }

    private suspend fun generateImageUrl(temperature: Int): Result<String> {
        return openAiDataSource.generateImagePrompt(temperature).fold(
            onSuccess = {
                val imageUrl = dalleDataSource.requestOutfitImageGeneration(it)
                val file = fileDataSource.downloadImageUrlToFile(imageUrl.getOrNull().orEmpty())
                val downloadUrl = firebaseDataSource.uploadAndFetchDownloadUrl(file)
                firebaseDataSource.uploadFireStore(temperature, downloadUrl)
                file.delete()
                Result.success(downloadUrl)
            },
            onFailure = { Result.failure(it) }
        )
    }
}
