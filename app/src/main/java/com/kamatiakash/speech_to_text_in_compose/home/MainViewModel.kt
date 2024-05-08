package com.kamatiakash.speech_to_text_in_compose.home

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kamatiakash.speech_to_text_in_compose.ApiService
import com.kamatiakash.speech_to_text_in_compose.ResultState
import com.kamatiakash.speech_to_text_in_compose.model.VoiceDataDto
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseTypes
import com.kamatiakash.speech_to_text_in_compose.safeApiCall
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    fun changeTextValue(text: String) {
        viewModelScope.launch {
            state = state.copy(
                text = text
            )
        }
    }

    fun getApiData(inputText: String) {

        viewModelScope.launch(Dispatchers.IO) {


//            var result = safeApiCall {
//                apiService.getData(inputText)
//            }


            var result = ResultState.Success(
                data = VoiceResponseDto(
                    type = VoiceResponseTypes.product,
                    data = listOf(
                        VoiceDataDto(productId = "11", productName = "12", description = "13")
                    )
                )
            )

            when (result) {
                is ResultState.Success -> {

                    Log.d("DataResponse", result.data.toString())

                    state = state.copy(
                        voiceResponseDto = result.data
                    )

                }

//                is ResultState.Error -> {
//
//                    Log.d("DataResponse", result.data.toString())
//
//                }
            }

        }


    }

    fun reset() {
        viewModelScope.launch {
            state = state.copy(
                text = null,
                voiceResponseDto = null
            )
        }
    }


}