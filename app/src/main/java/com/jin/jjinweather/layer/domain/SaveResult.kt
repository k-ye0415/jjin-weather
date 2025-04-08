package com.jin.jjinweather.layer.domain

import java.lang.Exception

sealed class SaveResult {
    object Success : SaveResult()
    data class Failure(val reason: SaveError) : SaveResult()
}

sealed class SaveError {
    object DbUnavailable : SaveError()
    object DuplicatedData : SaveError()
    data class Unknown(val exception: Exception) : SaveError()
}
