package com.kamatiakash.speech_to_text_in_compose

sealed interface ResultState<out T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Error(val exception: Throwable? = null) : ResultState<Nothing>
}

internal fun ResultState.Error.getErrorMessage() = this.exception?.message.orEmpty()
