package com.jin.jjinweather.feature.fileimpl.data

import android.content.Context
import android.util.Log
import com.jin.jjinweather.feature.file.FileDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.File
import java.util.UUID

class FileDataSourceImpl(context: Context) : FileDataSource {
    private val context = context.applicationContext

    override suspend fun downloadImageUrlToFile(imageUrl: String): File {
        return withContext(Dispatchers.IO) {
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

            val contentType = response?.header(HEADER)
            val fileExtension = findExtensionByType(contentType.orEmpty())

            val fileName = "${UUID.randomUUID()}.$fileExtension"
            val file = File(context.cacheDir, fileName)

            file.outputStream().use { output ->
                inputStream?.copyTo(output)
            }
            file
        }
    }

    private companion object {
        val TAG = "FileDataSource"
        val HEADER = "Content-Type"

        fun findExtensionByType(type: String): String {
            return when (type) {
                "image/png" -> "png"
                "image/jpeg" -> "jpg"
                else -> "jpg" // default fallback
            }
        }
    }
}
