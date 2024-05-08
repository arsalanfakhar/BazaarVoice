package com.kamatiakash.speech_to_text_in_compose.home

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kamatiakash.speech_to_text_in_compose.SpeechRecognizerContract
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(
    navigateToProduct: (data: VoiceResponseDto) -> Unit,
    viewModel: MainViewModel
) {


    val permissionState = rememberPermissionState(
        permission = Manifest.permission.RECORD_AUDIO
    )
    SideEffect {
        permissionState.launchPermissionRequest()
    }

    val speechRecognizerLauncher = rememberLauncherForActivityResult(
        contract = SpeechRecognizerContract(),
        onResult = {
            viewModel.changeTextValue(it.toString())
        }
    )


//    val context = LocalContext.current
//    val density = LocalDensity.current
//    val loaderSize = with(density) { 60.dp.toPx() }
    val loaderModifier = Modifier.size(60.dp)


    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            if (viewModel.state.text != null) {
                Text(
                    text = viewModel.state.text!!,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.height(45.dp))

            Button(onClick = {
                if (permissionState.status.isGranted) {
                    speechRecognizerLauncher.launch(Unit)
                } else
                    permissionState.launchPermissionRequest()
            }) {
                Text(text = "Speak")
            }

        }

        // Loader
        CircularLoader(
            isVisible = viewModel.state.isLoading,
            modifier = loaderModifier
        )
    }


    // Observe state changes and navigate to product screen when voiceResponseDto is not null
    LaunchedEffect(viewModel.state.voiceResponseDto) {
        viewModel.state.voiceResponseDto?.let {
            navigateToProduct(it)
        }
    }


}

@Composable
fun CircularLoader(
    isVisible: Boolean,
    modifier: Modifier = Modifier
) {

    AnimatedVisibility(visible = isVisible) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }

}