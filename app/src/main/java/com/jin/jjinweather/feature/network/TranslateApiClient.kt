package com.jin.jjinweather.feature.network

import android.util.Log
import com.google.auth.oauth2.GoogleCredentials
import com.google.cloud.translate.Translate
import com.google.cloud.translate.TranslateOptions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object TranslateApiClient {
    @Volatile
    private var translateService: Translate? = null

    fun initialize() {
        if (translateService == null) {
            synchronized(this) {
                if (translateService == null) {
                    translateService = try {
                        val path = System.getProperty("GOOGLE_TRANSLATE_KEY_PATH") ?: return
                        TranslateOptions.newBuilder()
                            .setCredentials(GoogleCredentials.fromStream(File(path).inputStream()))
                            .build()
                            .service
                    } catch (e: Exception) {
                        Log.e("TranslateApiClient", "초기화 실패", e)
                        null
                    }
                }
            }
        }
    }

    suspend fun translateText(text: String): String = withContext(Dispatchers.IO) {
        return@withContext try {
            val service = translateService ?: return@withContext text
            val translation = service.translate(
                text,
                Translate.TranslateOption.targetLanguage("ko")
            )
            translation.translatedText
        } catch (e: Exception) {
            Log.e("TranslateApiClient", "번역 실패: $text", e)
            text // 번역 실패 시 원본 텍스트 반환
        }
    }
}
