package com.kamatiakash.speech_to_text_in_compose

import java.io.IOException

class NoConnectivityException : IOException() {
    override val message: String
        get() =
            "No network available, please check your WiFi or Data connection"
}

class NoInternetException : IOException() {
    override val message: String
        get() =
            "No internet available, please check your connected WIFi or Data"
}

class UnAuthorizedException : IOException()

class ForbiddenException : IOException()

class RefreshTokenExpiredException : Exception()
