package com.jin.jjinweather.feature.firebaseimpl.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.jin.jjinweather.feature.firebase.FirebaseDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class FirebaseDataSourceImpl(
    private val storage: FirebaseStorage,
    private val firestore: FirebaseFirestore
) : FirebaseDataSource {
    override suspend fun uploadAndFetchDownloadUrl(file: File): String {
        return withContext(Dispatchers.IO) {
            try {
                val fileName = file.name
                val imageRef = storage.reference.child("images/$fileName")

                imageRef.putFile(Uri.fromFile(file)).await()

                imageRef.downloadUrl.await().toString()
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload and fetch downloadUrl: $e")
                ""
            }
        }
    }

    override suspend fun uploadFireStore(temperature: Int, imageUrl: String) {
        withContext(Dispatchers.IO) {
            try {
                val key = "$temperature"
                val data = mapOf(key to FieldValue.arrayUnion(imageUrl))

                val docRef = firestore.collection(COLLECTION_NAME).document(DOCUMENT_NAME)
                docRef.set(data, SetOptions.merge())
            } catch (e: Exception) {
                Log.e(TAG, "Failed to upload to Firestore (temperature=$temperature)", e)
            }
        }
    }

    override suspend fun fetchImagesByTemperature(temperature: Int): List<String> {
        return withContext(Dispatchers.IO) {
            try {
                val docRef = firestore.collection(COLLECTION_NAME).document(DOCUMENT_NAME)

                val snapshot = docRef.get().await()
                if (snapshot.exists()) {
                    val key = temperature.toString()
                    val imageUrls = snapshot.get(key) as? List<String>
                    imageUrls ?: emptyList()
                } else {
                    emptyList()
                }
            } catch (e: Exception) {
                Log.e(TAG, "Failed to fetch images from Firestore (temperature=$temperature)", e)
                emptyList()
            }
        }
    }

    private companion object {
        val TAG = "FirebaseDataSource"
        val COLLECTION_NAME = "outfit"
        val DOCUMENT_NAME = "images"
    }
}
