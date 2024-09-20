package com.example.fooddelivery.ui.presentation.FoodDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.model.Yemekler

@Composable
fun FoodDetailScreen(navController: NavController, food: Yemekler) {


    Scaffold { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text("food id ${food.yemek_id}",)
            Text("name${food.yemek_adi}",)
            Text("price ${food.yemek_fiyat}",)
        }

    }

}