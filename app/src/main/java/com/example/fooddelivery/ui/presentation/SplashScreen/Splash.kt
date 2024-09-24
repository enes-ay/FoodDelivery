package com.example.fooddelivery.ui.presentation.SplashScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.example.fooddelivery.ui.presentation.Auth.Login.LoginViewmodel
import kotlinx.coroutines.delay

@Composable
fun Splash(navController: NavController) {
    val loginViewmodel: LoginViewmodel = hiltViewModel()

    // Load the Lottie animation from the JSON file
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("splash_animation.json"))

    LaunchedEffect(Unit) {
        val isLoggedIn = loginViewmodel.userLoggedIn.value
        delay(2000L)
        if (isLoggedIn) {
            navController.navigate("foodList") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center,
    ) {
        // Display the Lottie animation in the center of the splash screen
        LottieAnimation(
            composition = composition,
            iterations = 2 // You can loop the animation by changing this value
        )
    }
}
