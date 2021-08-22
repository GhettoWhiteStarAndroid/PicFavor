package com.ghettowhitestar.picfavor.data.remote

import retrofit2.HttpException
import java.io.IOException

object NetworkHelper {

    suspend fun <T> safeApiCall( apiCall: suspend () -> T): ResultWrapper<T> {
        return try {
                ResultWrapper.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = throwable.message()
                        ResultWrapper.GenericError(code, errorResponse)
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null)
                    }
                }
            }
        }

}