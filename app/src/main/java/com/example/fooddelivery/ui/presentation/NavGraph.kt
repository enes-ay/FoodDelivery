package com.example.fooddelivery.ui.presentation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.ui.presentation.Auth.Login.Login
import com.example.fooddelivery.ui.presentation.Auth.Register.Register
import com.example.fooddelivery.ui.presentation.Cart.CartScreen
import com.example.fooddelivery.ui.presentation.Favorites.Favorites
import com.example.fooddelivery.ui.presentation.FoodDetail.FoodDetailScreen
import com.example.fooddelivery.ui.presentation.FoodList.FoodListScreen
import com.example.fooddelivery.ui.presentation.Profile.ProfileScreen
import com.example.fooddelivery.ui.presentation.SplashScreen.Splash
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "splash" ){
        composable("register"){
            Register(navController = navController)
        }
        composable("splash"){
            Splash(navController = navController)
        }
        composable("login"){
            Login(navController = navController)
        }
        composable("foodList"){
            FoodListScreen(navController)
        }
        composable("foodDetail/{food}",
            arguments = listOf( navArgument("food"){type= NavType.StringType})
        ){
            val json = it.arguments?.getString("food")
            val food = Gson().fromJson(json, Yemekler::class.java)
            FoodDetailScreen(navController, food)
        }
        composable("cart"){
            CartScreen(navController = navController)
        }
        composable("favorites"){
            Favorites(navController)
        }
        composable("profile"){
            ProfileScreen(navController)
        }
    }
}