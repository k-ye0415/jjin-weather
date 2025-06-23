package com.jin.jjinweather.feature.outfit.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.jin.jjinweather.feature.outfit.domain.OutfitRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
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
//                val imageUrl = dalleDataSource.requestOutfitImageGeneration(it)
//                val file = downloadImageUrlToFile(imageUrl.getOrNull().orEmpty())
                val file = File(context.cacheDir, "0add5a9b-6090-47b1-a209-e0991ed1b348.png")
                val downloadUrl = uploadStorage(file)
//                val downloadUrl = "https://firebasestorage.googleapis.com/v0/b/jjin-weather.firebasestorage.app/o/images%2F0add5a9b-6090-47b1-a209-e0991ed1b348.png?alt=media&token=9e811776-ef30-4b5f-a501-4b734f761c94"
                uploadFireStore(temperature, downloadUrl)
//                imageUrl
                Result.success("https://picsum.photos/1792/1024?random=1") // test
            },
            onFailure = { Result.failure(it) }
        )
    }

    private suspend fun downloadImageUrlToFile(imageUrl: String): File =
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
            return@withContext file
        }

    private suspend fun uploadStorage(file: File): String =
        withContext(Dispatchers.IO) {
            val storage = Firebase.storage
            val fileName = file.name
            val imageRef = storage.reference.child("images/$fileName")

            imageRef.putFile(Uri.fromFile(file)).await()

            return@withContext imageRef.downloadUrl.await().toString()
        }


    private suspend fun uploadFireStore(temperature: Int, imageUrl: String) {
        withContext(Dispatchers.IO) {
            // FIXME DI
            val firestore = Firebase.firestore("weather")
            val key = "$temperature"
            val data = mapOf(key to FieldValue.arrayUnion(imageUrl))

            val docRef = firestore.collection("outfit").document("images")
            docRef.set(data, SetOptions.merge())

//            file.delete()
        }
    }


    private companion object {
        val TAG = "OutfitRepository"
    }
}
