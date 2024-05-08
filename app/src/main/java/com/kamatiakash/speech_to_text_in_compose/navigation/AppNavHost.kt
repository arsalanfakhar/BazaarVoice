package com.kamatiakash.speech_to_text_in_compose.navigation

import android.util.Log
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamatiakash.product.ProductScreen
import com.kamatiakash.speech_to_text_in_compose.home.MainScreen
import com.kamatiakash.speech_to_text_in_compose.home.MainViewModel
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseTypes

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    mainViewModel: MainViewModel,
    navController: NavHostController,
    startDestination: String = NavigationItem.Home.route,
) {



    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(NavigationItem.Home.route) {
            MainScreen(navigateToProduct = {
                Log.d("DataResponse", "routed")

                navController.navigate(NavigationItem.SEARCH.route)
            }, mainViewModel)
        }

        composable(NavigationItem.SEARCH.route) {

            ProductScreen(
                data = mainViewModel.state.voiceResponseDto
                    ?: VoiceResponseDto(type = VoiceResponseTypes.product, data = emptyList()),
                mainViewModel
            )

        }


    }
}


@Composable
fun BackPressHandler(
    backPressedDispatcher: OnBackPressedDispatcher? =
        LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }

    DisposableEffect(key1 = backPressedDispatcher) {
        backPressedDispatcher?.addCallback(backCallback)

        onDispose {
            backCallback.remove()
        }
    }
}