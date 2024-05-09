package com.kamatiakash.speech_to_text_in_compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoiceResponseDto(
    val type: VoiceResponseTypes,
    val data: List<VoiceDataDto>
) : Parcelable

@Parcelize
data class VoiceDataDto(
    val productId: String = "",
    val productName: String = "",
    val price: String = "",
    val description: String = "",
    val orderNumber: String = "",
    val orderStatus: String = "",
    val orderDate: String = "",
    val expectedDeliveryDate: String = ""
) : Parcelable


enum class VoiceResponseTypes {
    product,
    order_status
}



