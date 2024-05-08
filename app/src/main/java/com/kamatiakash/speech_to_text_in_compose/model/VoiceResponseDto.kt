package com.kamatiakash.speech_to_text_in_compose.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class VoiceResponseDto(
    val type: VoiceResponseTypes,
    val data: List<VoiceDataDto>
):Parcelable
@Parcelize
data class VoiceDataDto(
    val productId: String = "",
    val productName: String = "",
    val price: String = "",
    val description: String = "",
    val orderNumber: String = "",
    val status: String = "",
    val expectedDelivery: String = ""
):Parcelable


enum class VoiceResponseTypes {
    product,
    order_status
}



data class EmployeeResponse(
    val status: String,
    val data: List<Employee>,
    val message: String
)

data class Employee(
    val id: Int,
    val employee_name: String,
    val employee_salary: Int,
    val employee_age: Int,
    val profile_image: String
)
