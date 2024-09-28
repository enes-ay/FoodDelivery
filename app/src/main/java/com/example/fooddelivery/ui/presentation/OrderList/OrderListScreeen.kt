package com.example.fooddelivery.ui.presentation.OrderList

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController

@Composable
fun OrderListScreen(navController: NavHostController) {
    Scaffold (modifier= Modifier.fillMaxSize()) { paddingValues ->

        LazyColumn (modifier= Modifier.fillMaxSize().padding(paddingValues)){

        }
    }
}