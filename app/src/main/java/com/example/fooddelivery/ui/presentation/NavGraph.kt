package com.example.fooddelivery.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.ui.presentation.Cart.CartScreen
import com.example.fooddelivery.ui.presentation.Favorites.Favorites
import com.example.fooddelivery.ui.presentation.FoodDetail.FoodDetailScreen
import com.example.fooddelivery.ui.presentation.FoodList.FoodListScreen
import com.google.gson.Gson

@Composable
fun NavGraph(navController: NavHostController) {

    NavHost(navController = navController, startDestination = "foodList" ){
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
            Favorites()
        }
        composable("profile"){
            ProfileScreen()
        }
    }
}