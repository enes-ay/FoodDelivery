package com.example.fooddelivery.ui.presentation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun NavGraph(paddingValues: PaddingValues, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "foodList" ){
        composable("foodList"){
            FoodListScreen()
        }
        composable("foodDetail"){
            FoodListScreen()
        }
        composable("cart"){
            FoodListScreen()
        }
        composable("favorites"){
            Favorites()
        }
    }
}