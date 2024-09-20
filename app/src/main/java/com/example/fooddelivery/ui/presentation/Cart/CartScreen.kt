package com.example.fooddelivery.ui.presentation.Cart

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.data.model.SepetYemekler
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(modifier: Modifier = Modifier, navController:NavHostController) {

    val cartViewmodel: CartViewmodel = hiltViewModel()
    val cartFoods by cartViewmodel.foods.observeAsState(initial = listOf())
    val groupedFoods = cartFoods.groupBy { it.yemek_adi }
        .mapValues { entry -> entry.value.sumOf { it.yemek_siparis_adet } }

    LaunchedEffect(true) {
        cartViewmodel.getCartFoods("enes") // current user will be added
        Log.e("cartsize", " ${cartFoods.size}")
    }
    Scaffold(topBar = { TopAppBar(title = { Text(text = "Cart") }) },
        bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        cartFoods.map {
            it.yemek_adi
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            items(groupedFoods.keys.toList()) { yemekAdi ->
                val siparisAdet = groupedFoods[yemekAdi] ?: 0
                // İlgili `CartItem` oluştur
                val food = cartFoods.first { it.yemek_adi == yemekAdi }
                CartItem(
                    food = food.copy(yemek_siparis_adet = siparisAdet),
                    onClick = {
                        Log.e("cart", "test")
                    },
                    onDelete = {
                        cartViewmodel.deleteFoodFromCart(food.sepet_yemek_id, food.kullanici_adi)
                    })

            }
        }

    }

}


@Composable
fun CartItem(
    modifier: Modifier = Modifier, food: SepetYemekler,
    onClick: () -> Unit, onDelete: () -> Unit
) {

    Row(modifier = Modifier
        .fillMaxWidth()
        .height(120.dp)
        .padding(4.dp)
        .clickable { onClick }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f)
        ) {
            Text(text = food.yemek_adi)
            GlideImage(
                imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}",
                contentScale = ContentScale.Crop
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .weight(2f),
            horizontalAlignment = Alignment.End
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(text = "${food.yemek_fiyat}")
                Text(text = "${food.yemek_siparis_adet}")
                IconButton(
                    onClick = onDelete
                ) {
                    Icon(imageVector = Icons.Default.Delete, contentDescription = "delete button")
                }
            }
        }
    }
}