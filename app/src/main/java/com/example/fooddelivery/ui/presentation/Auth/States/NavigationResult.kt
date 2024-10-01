package com.example.fooddelivery.ui.presentation.Auth.States

sealed class NavigationResult {
    object NavigateToHomeScreen : NavigationResult()
    object NoNavigation : NavigationResult()
}