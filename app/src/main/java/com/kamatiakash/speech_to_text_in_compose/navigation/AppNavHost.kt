package com.kamatiakash.speech_to_text_in_compose.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.core.os.bundleOf
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kamatiakash.product.ProductScreen
import com.kamatiakash.speech_to_text_in_compose.home.MainScreen
import com.kamatiakash.speech_to_text_in_compose.home.MainViewModel
import com.kamatiakash.speech_to_text_in_compose.model.VoiceResponseDto

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
//            val voiceResponseDto =
//                backStackEntry.arguments?.getParcelable<VoiceResponseDto>("voiceResponseDto")

            ProductScreen(data = mainViewModel.state.voiceResponseDto!!,mainViewModel)

        }


    }
}