package com.kamatiakash.speech_to_text_in_compose.home

import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto

data class MainScreenState(
    val text:String? = null,
    val voiceResponseDto: VoiceResponseDto? = null
)
