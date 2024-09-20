package com.example.fooddelivery.ui.presentation.FoodList

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.utils.Constants
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(navController: NavHostController) {
    val foodListViewmodel: FoodListViewmodel = hiltViewModel()
    val foodList by foodListViewmodel.foodList.observeAsState()

    LaunchedEffect(foodListViewmodel.foodList) {
        foodListViewmodel.getAllFoods()
    }
    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Food List") }) },
        bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            foodList?.let {

                for (food in foodList!!) {
                    println("food ${food.yemek_id} ${food.yemek_adi}")
                }
                items(it.count()) {
                    val food = foodList!![it]
                    FoodItemCard(food = food,
                        onAddToCart = {
                            foodListViewmodel.addFoodToCart(
                                food.yemek_adi,
                                food.yemek_resim_adi,
                                food.yemek_fiyat,
                                1,
                                "enes"
                            )
                        },
                        onRemoveFromCart = {
                            // foodListViewmodel.deleteFoodFromCart(food.yemek_id.toInt(),"enes")
                        })
                }
            }

        }

    }
}

@Composable
fun FoodItemCard(
    food: Yemekler,
    initialCount: Int = 0, // Use this to initialize the count, maybe fetched from the cart
    onAddToCart: (Yemekler) -> Unit,
    onRemoveFromCart: (Yemekler) -> Unit
) {
    var count by remember { mutableStateOf(initialCount) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                // Navigate to detail screen if needed
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image using Glide
        GlideImage(
            modifier = Modifier.size(120.dp),
            imageModel = "${Constants.IMAGE_BASE_URL}${food.yemek_resim_adi}",
            contentDescription = "${food.yemek_adi} image"
        )

        // Food info (name and price)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(text = food.yemek_adi, fontSize = 22.sp)
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 15.dp), // Add padding to avoid edge overlap
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // Ensure equal spacing
        ) {
            Text(text = "${food.yemek_fiyat}₺", fontSize = 22.sp)

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (count > 0) {
                    IconButton(
                        modifier = Modifier.size(36.dp), // Set fixed size for the button
                        onClick = {
                            count--
                            onRemoveFromCart(food) // Call the API to remove the item
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
                    Text(
                        text = "$count",
                        fontSize = 22.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                IconButton(
                    modifier = Modifier.size(36.dp),
                    onClick = {
                        count++
                        onAddToCart(food) // Call the API to add the item
                    }
                ) {
                    Icon(Icons.Default.AddCircle, contentDescription = "Increase Buttonb")
                }
            }
        }
    }
}

