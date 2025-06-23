package com.jin.jjinweather.feature.file

import java.io.File

interface FileDataSource {
    suspend fun downloadImageUrlToFile(imageUrl: String): File
}
