package com.ghettowhitestar.picfavor.data.remote

sealed class ResultWrapper<out T> {
    data class Success<out T>(val value: T): ResultWrapper<T>()
    data class GenericError(val code: Int? = null, val errorMessage: String? = null): ResultWrapper<Nothing>()
    data class NetworkError (val errorMessage: String? = "Network Error"): ResultWrapper<Nothing>()
}
