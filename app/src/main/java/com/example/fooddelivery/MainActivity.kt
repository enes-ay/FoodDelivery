package com.example.fooddelivery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.ui.presentation.NavGraph
import com.example.fooddelivery.ui.presentation.Screens
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FoodDeliveryTheme {
                val navController = rememberNavController()

                Scaffold(
                    bottomBar = {
                        if (shouldShowBottomBar(navController)) {
                            BottomBar(navController)
                        }
                    }, modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            bottom = WindowInsets.navigationBars
                                .asPaddingValues()
                                .calculateBottomPadding()
                        ) // This adds padding for the system navigation
                ) { paddingValues ->
                    NavGraph(paddingValues, navController)
                }
            }
        }

    }
}


@Composable
fun shouldShowBottomBar(navController: NavController): Boolean {
    // Don't show the bottom bar on the login page
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    return currentRoute != "login"
}

@Composable
fun BottomBar(navController: NavController) {
    BottomNavigation (backgroundColor = Color.White) {
        val items = listOf(
            Screens.Home,
            Screens.Cart,
            Screens.Profile,
            Screens.Favorites,
        )
        items.forEach { item ->
            BottomNavigationItem(
                icon = {
                    when (item.label) {
                        "Home" -> Icon(Icons.Default.Home, contentDescription = "Home")
                        "Favorites" -> Icon(
                            Icons.Default.Favorite,
                            contentDescription = "Favorites"
                        )

                        "Cart" -> Icon(Icons.Default.ShoppingCart, contentDescription = "Cart")
                        "Profile" -> Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                },
                label = { Text(item.label) },
                selected = navController.currentDestination?.route == item.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}

