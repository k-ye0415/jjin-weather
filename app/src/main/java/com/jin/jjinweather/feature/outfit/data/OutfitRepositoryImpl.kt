package com.jin.jjinweather.feature.outfit.data

import android.content.Context
import android.util.Log
import com.jin.jjinweather.feature.outfit.domain.OutfitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.UUID

class OutfitRepositoryImpl(
    context: Context,
    private val openAiDataSource: OpenAiDataSource,
    private val dalleDataSource: DalleDataSource
) : OutfitRepository {

    private val context = context.applicationContext

    override suspend fun generateOutfitImageUrl(temperature: Int): Result<String> {
        return openAiDataSource.generateImagePrompt(temperature).fold(
            onSuccess = {
                val imageUrl = dalleDataSource.requestOutfitImageGeneration(it)
                downloadImageUrlToFile(imageUrl.getOrNull().orEmpty())
                imageUrl
//                Result.success("https://picsum.photos/1792/1024?random=1") // test
            },
            onFailure = { Result.failure(it) }
        )
    }

    private suspend fun downloadImageUrlToFile(imageUrl: String) {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val request = Request.Builder()
                .url(imageUrl)
                .build()

            val response = try {
                client.newCall(request).execute()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to download image: $e")
                null
            }

            val inputStream = try {
                response?.body?.byteStream()
            } catch (e: Exception) {
                Log.e(TAG, "Empty body")
                null
            }

            val contentType = response?.header("Content-Type")
            val fileExtension = when (contentType) {
                "image/png" -> "png"
                "image/jpeg" -> "jpg"
                else -> "jpg" // default fallback
            }

            val fileName = "${UUID.randomUUID()}.$fileExtension"
            val file = File(context.cacheDir, fileName)

            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
        }
    }

    private companion object {
        val TAG = "OutfitRepository"
    }
}
