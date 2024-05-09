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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.PaintingStyle
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kamatiakash.speech_to_text_in_compose.SpeechRecognizerContract
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto
import com.kamatiakash.speech_to_text_in_compose.ui.loader.InfinityLoader

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
                Text(text = "Ask AI Assistant")
            }

        }

        // Loader
//        CircularLoader(
//            isVisible = viewModel.state.isLoading,
//            modifier = loaderModifier
//        )


        InfinityLoader(
            isVisible = viewModel.state.isLoading,
            brush = Brush.horizontalGradient(
                colors = listOf(Color.Red, Color.Blue)
            ),
            modifier = Modifier
                .width(200.dp)
                .height(150.dp),
            glow = Glow(),
            placeholderColor = Color.Black.copy(.16f)
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


data class Glow(
    val radius: Dp = 8.dp,    // Controls glow size
    val xShifting: Dp = 0.dp, // Adjusts horizontal position
    val yShifting: Dp = 0.dp  // Adjusts vertical position
)

// Extend the DrawScope to utilize 'toPx()' and access the Canvas size
fun DrawScope.setupPaint(
    strokeWidth: Dp,
    strokeCap: StrokeCap,
    brush: Brush,
): Paint {
    return Paint().apply paint@{
        // Set anti-aliasing for smoother edges
        this@paint.isAntiAlias = true
        // Set the painting style to Stroke (outline)
        this@paint.style = PaintingStyle.Stroke
        // Set the stroke width by converting from Dp to pixels
        this@paint.strokeWidth = strokeWidth.toPx()
        // Set the stroke cap style
        this@paint.strokeCap = strokeCap

        // Apply the brush to the paint
        brush.applyTo(size, this@paint, 1f)
    }
}

fun DrawScope.drawPathSegment(pathSegment: Path, paint: Paint) {
    drawIntoCanvas { canvas ->
        canvas.drawPath(pathSegment, paint)
    }
}


fun DrawScope.drawPathPlaceholder(
    path: Path,
    strokeWidth: Dp,
    strokeCap: StrokeCap,
    placeholderColor: Color
) {
    drawPath(
        path = path,
        color = placeholderColor,
        style = Stroke(
            width = strokeWidth.toPx(),
            cap = strokeCap
        )
    )
}

fun Glow.applyToPaint(paint: Paint, density: Density) = with(density) {
    val frameworkPaint = paint.asFrameworkPaint()
    frameworkPaint.setShadowLayer(
        /* radius = */ radius.toPx(),
        /* dx = */ xShifting.toPx(),
        /* dy = */ yShifting.toPx(),
        /* shadowColor = */ android.graphics.Color.WHITE
    )
}


