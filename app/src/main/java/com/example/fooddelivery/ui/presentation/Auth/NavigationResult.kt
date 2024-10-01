package com.example.fooddelivery.ui.presentation.Auth

sealed class NavigationResult {
    object NavigateToHomeScreen : NavigationResult()
    object NoNavigation : NavigationResult()
}