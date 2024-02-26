package com.simma.simmaapp.model

import androidx.annotation.Keep

@Keep
sealed class Resource<out T>(message: String? = null, data: T? = null) {
    data class Success<out T>(val data: T) : Resource<T>(data = data)
    data class Error<out T>(val errorMessage: String) : Resource<T>(message = errorMessage)
    data class Loading<out T>(val isLoading: Boolean) : Resource<T>()
}