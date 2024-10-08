package com.example.fooddelivery.ui.presentation.Cart

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.ui.theme.primaryColor
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(navController: NavHostController) {

    val cartViewmodel: CartViewmodel = hiltViewModel()
    val cartFoods by cartViewmodel.foods.observeAsState(initial = listOf())
    val groupedFoods = cartFoods.groupBy { it.yemek_adi }
        .mapValues { entry -> entry.value.sumOf { it.yemek_siparis_adet } }

    val totalPrice = cartFoods.sumOf { it.yemek_fiyat * it.yemek_siparis_adet }

    LaunchedEffect(true) {
        cartViewmodel.getCartFoods("enes") // current user will be added
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.cartLabel),
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
            )
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->

        if (cartFoods.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No food found")
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {

            LazyColumn(
                modifier = Modifier
                    .weight(4f)
                    .fillMaxWidth()
            ) {
                items(groupedFoods.keys.toList()) { yemekAdi ->
                    val siparisAdet = groupedFoods[yemekAdi] ?: 0
                    val food = cartFoods.first { it.yemek_adi == yemekAdi }

                    CartItem(
                        food = food.copy(yemek_siparis_adet = siparisAdet),
                        onClick = {
                            var foodJson = Gson().toJson(food)
                            navController.navigate("foodDetail/$foodJson") {
                                popUpTo("cart")
                            }
                        },
                        onDelete = {
                            cartViewmodel.deleteFoodFromCart(
                                food.sepet_yemek_id,
                                food.kullanici_adi
                            )
                        },
                        onAddToCart = {
                            cartViewmodel.addFoodToCart(
                                food.yemek_adi, food.yemek_resim_adi, food.yemek_fiyat,
                                food.yemek_siparis_adet, food.kullanici_adi
                            )
                        }
                    )
                }
            }

            // Fixed bottom section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Confirm Cart Button
                Button(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 200.dp)
                        .padding(end = 10.dp),
                    onClick = { /* Confirm Cart action */ },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(
                        text = stringResource(id = R.string.confirmCart),
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = stringResource(id = R.string.total)+" ${totalPrice}₺",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

        }
    }
}


@Composable
fun CartItem(
    food: SepetYemekler,
    onClick: () -> Unit,
    onDelete: () -> Unit,
    onAddToCart: () -> Unit,
    initialCount: Int = 0, // Use this to initialize the count, maybe fetched from the cart

) {
    var count by remember { mutableStateOf(food.yemek_siparis_adet) }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp) // Add padding around the card
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(16.dp)), // Add shadow and round corners
        shape = RoundedCornerShape(16.dp), // Rounded corners
        // elevation = 8.dp, // Elevation for the card shadow
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .height(120.dp)
                .padding(vertical = 4.dp, horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f)
            ) {

                GlideImage(
                    modifier = Modifier
                        .size(160.dp)
                        .weight(1f),
                    imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}",
                    contentScale = ContentScale.Crop,
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {

                Text(
                    text = food.yemek_adi,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                Text(
                    text = "${food.yemek_fiyat*food.yemek_siparis_adet}₺",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
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
                    horizontalArrangement = Arrangement.SpaceAround,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (count == 1) {
                            IconButton(
                                modifier = Modifier.size(36.dp), // Set fixed size for the button
                                onClick = {
                                    count--
                                    onDelete()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_delete),
                                    contentDescription = "icon delete"
                                )
                            }
                        } else if (count > 0) {
                            IconButton(
                                modifier = Modifier.size(36.dp), // Set fixed size for the button
                                onClick = {
                                    count--
                                    onDelete()
                                }
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_remove_circle),
                                    contentDescription = "icon remove"
                                )
                            }
                        } else {
                            Spacer(modifier = Modifier.size(36.dp)) // Reserve space when "-" button is not visible
                        }

                        if (count > 0) {
                            androidx.compose.material3.Text(
                                text = "$count",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                        }

                        IconButton(
                            modifier = Modifier.size(36.dp),
                            onClick = {
                                count++
                                onAddToCart()
                            }
                        ) {
                            Icon(Icons.Default.AddCircle, contentDescription = "Increase Button")
                        }
                    }
                }
            }
        }
    }


}