package com.kamatiakash.speech_to_text_in_compose.navigation

enum class Screen {
    HOME,
    ORDER_HISTORY,
    SEARCH
}

sealed class NavigationItem(val route: String) {
    object Home : NavigationItem(Screen.HOME.name)
    object SEARCH : NavigationItem(Screen.SEARCH.name)
}