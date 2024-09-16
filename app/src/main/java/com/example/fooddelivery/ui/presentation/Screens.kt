package com.example.fooddelivery.ui.presentation

import androidx.annotation.DrawableRes

sealed class Screens(val route: String,  val label :String) {
    object Profile : Screens("foodList","Profile")
    object Home : Screens("foodList", "Home")
    object Favorites : Screens("favorites", "Favorites")
    object Cart : Screens("cart", "Cart")
}