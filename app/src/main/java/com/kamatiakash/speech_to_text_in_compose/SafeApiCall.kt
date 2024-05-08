package com.kamatiakash.speech_to_text_in_compose

import java.io.IOException
@Suppress("TooGenericExceptionCaught")
internal suspend fun <T> safeApiCall(apiCall: suspend () -> T): ResultState<T> {
    return try {
        val response = apiCall.invoke()
        ResultState.Success(response)
    } catch (throwable: Throwable) {
        when (throwable) {
            is IOException -> ResultState.Error(NoInternetException())
            else -> ResultState.Error(throwable)
        }
    }
}
