package com.kamatiakash.speech_to_text_in_compose.ui.loader

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathMeasure
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.kamatiakash.speech_to_text_in_compose.home.Glow
import com.kamatiakash.speech_to_text_in_compose.home.applyToPaint
import com.kamatiakash.speech_to_text_in_compose.home.drawPathPlaceholder
import com.kamatiakash.speech_to_text_in_compose.home.drawPathSegment
import com.kamatiakash.speech_to_text_in_compose.home.setupPaint

@Composable
fun InfinityLoader(
    // Parameters
    isVisible: Boolean,
    modifier: Modifier,
    brush: Brush,
    duration: Int = 10_000,
    strokeWidth: Dp = 4.dp,
    strokeCap: StrokeCap = StrokeCap.Round,
    glow: Glow? = null,
    placeholderColor: Color? = null,
) {

    AnimatedVisibility(visible = isVisible) {


        // Set up infinite animation
        val infiniteTransition = rememberInfiniteTransition("PathTransition")

        // Animate path completion
        val pathCompletion by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2f,
            animationSpec = infiniteRepeatable(
                animation = tween(duration, easing = LinearEasing)
            ),
            label = "PathCompletion"
        )

        Canvas(modifier) {
            // Create path and calculate segment
            val path = createPath(size.width, size.height)
            val pathSegment = calculatePathSegment(path, pathCompletion)

            // Set up paint for drawing
            val paint = setupPaint(strokeWidth, strokeCap, brush)

            // Apply glow effect, if provided
            glow?.applyToPaint(paint, this)

            // Draw placeholder, if color is provided
            placeholderColor?.let { color ->
                drawPathPlaceholder(path, strokeWidth, strokeCap, color)
            }

            // Draw the path segment
            drawPathSegment(pathSegment, paint)
        }
    }

}

fun calculatePathSegment(path: Path, pathCompletion: Float): Path {
    // Create a PathMeasure instance for the given path
    val pathMeasure = PathMeasure().apply {
        setPath(path, false)
    }

    // Create a new Path to store the segment
    val pathSegment = Path()

    // Calculate the distance to stop drawing the path segment
    val stopDistance = when {
        (pathCompletion < 1f) -> (pathCompletion * pathMeasure.length)
        else -> pathMeasure.length
    }

    // Calculate the distance to start drawing the path segment
    val startDistance = when {
        (pathCompletion > 1f) -> ((pathCompletion - 1f) * pathMeasure.length)
        else -> 0f
    }

    // Retrieve the segment of the path based on start and stop distances
    pathMeasure.getSegment(startDistance, stopDistance, pathSegment, true)
    return pathSegment
}

fun createPath(width: Float, height: Float): Path {
    return Path().apply {
        // Move to Center(c)
        moveTo((width / 2), (height / 2))
        // Draw the right side
        cubicTo(
            x1 = width, y1 = 0f, // 1
            x2 = width, y2 = height, // 2
            x3 = (width / 2), (height / 2) // Center(c)
        )
        // Draw the left side
        cubicTo(
            x1 = 0f, y1 = 0f, // 3
            x2 = 0f, y2 = height, // 4
            x3 = (width / 2), (height / 2) // Center(c)
        )
    }
}