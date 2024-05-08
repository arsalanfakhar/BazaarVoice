package com.kamatiakash.speech_to_text_in_compose

import com.kamatiakash.speech_to_text_in_compose.model.EmployeeResponse
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("wit/intents")
    suspend fun getData(@Query("text") text: String): VoiceResponseDto
}