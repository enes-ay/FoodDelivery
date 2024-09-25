package com.example.fooddelivery.ui.presentation.FoodList

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
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
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.ui.presentation.Favorites.FavoritesViewmodel
import com.example.fooddelivery.ui.theme.primaryColor
import com.example.fooddelivery.utils.Constants
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(navController: NavHostController) {
    val foodListViewmodel: FoodListViewmodel = hiltViewModel()
    val foodFavoritesViewmodel: FavoritesViewmodel = hiltViewModel()
    val foodList by foodListViewmodel.foodList.observeAsState() // Tüm yemekler
    var searchQuery by remember { mutableStateOf("") } // Arama terimini saklar
    val filteredFoodList = foodList?.filter { food ->
        food.yemek_adi.contains(searchQuery, ignoreCase = true)
    } ?: listOf() // Arama terimine göre filtreleme yapar
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    LaunchedEffect(foodListViewmodel.foodList) {
        foodListViewmodel.getAllFoods()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            Column {
                TopAppBar(title = { Text(text = "Food List", color = Color.White, fontWeight = FontWeight.Bold) },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor))
                TextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    placeholder = { Text(text = "Search for food...") },
                    leadingIcon = {
                        if (isFocused) {
                            IconButton(onClick = {
                                searchQuery = ""
                                focusManager.clearFocus() // Focusu temizleyerek aramadan çık
                            }) {
                                Icon(
                                    imageVector = Icons.Default.ArrowBack, // Geri oku göster
                                    contentDescription = "Back Icon"
                                )
                            }
                        }
                      else{
                            Icon(
                                imageVector = Icons.Default.Search, // Arama ikonunu göster
                                contentDescription = "Search Icon"
                            )
                        }
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = {
                                searchQuery = ""
                            }) {
                                Icon(
                                    imageVector = Icons.Default.Close,
                                    contentDescription = "Clear Search"
                                )
                            }
                        }
                    },
                    singleLine = true, // Tek satırlık giriş
                    shape = RoundedCornerShape(8.dp), // Köşeleri yuvarlatılmış
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        // placeholderColor = Color.Gray // Placeholder rengi
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                        .shadow(4.dp, RoundedCornerShape(8.dp))
                        .onFocusChanged { focusState ->
                            isFocused = focusState.isFocused // Focus durumunu takip et
                        }
                )

            }
        },
        bottomBar = { BottomBar(navController = navController) }
    ) { paddingValues ->
        LazyVerticalGrid(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            columns = GridCells.Fixed(2)
        ) {
            // Filtrelenmiş listeyi kullanarak öğeleri göster
            items(filteredFoodList.count()) {
                val food = filteredFoodList[it]
                FoodItemCard(
                    food = food,
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
                        foodListViewmodel.deleteFoodFromCart(food.yemek_id.toInt(), "enes")
                    },
                    onClick = {
                        val foodJson = Gson().toJson(food)
                        navController.navigate("foodDetail/$foodJson") {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = false
                        }
                    }
                    ,
                    onFavoriteClick = {
                        foodFavoritesViewmodel.saveFavFood(food.yemek_adi, food.yemek_fiyat, food.yemek_id, food.yemek_resim_adi)
                    }
                )
            }
        }
    }
}


@Composable
fun FoodItemCard(
    food: Yemekler,
    initialCount: Int = 0,
    onAddToCart: () -> Unit,
    onRemoveFromCart: (Yemekler) -> Unit,
    onClick: () -> Unit,
    onFavoriteClick: (Yemekler) -> Unit // New callback for favorite button
) {
    var count by remember { mutableStateOf(initialCount) }
    var isFavorite by remember { mutableStateOf(false) } // Track if the item is favorited

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(10.dp)
            .clickable { onClick() }
            .shadow(8.dp, RoundedCornerShape(16.dp)),
        shape = RoundedCornerShape(16.dp),
    ) {

        Box {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onClick()
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "${food.yemek_fiyat}₺", fontSize = 23.sp)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (count == 0) {
                            Button(
                                onClick = {
                                    count++
                                    onAddToCart()
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .wrapContentWidth()
                                    .padding(top = 8.dp),
                                shape = RoundedCornerShape(4.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.primary,
                                    contentColor = Color.White
                                )
                            ) {
                                Text(
                                    text = "Add to cart",
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        } else {
                            IconButton(
                                modifier = Modifier
                                    .size(43.dp)
                                    .padding(top = 14.dp),
                                onClick = {
                                    count--
                                    onRemoveFromCart(food)
                                }
                            ) {
                                Icon(
                                    painter = painterResource(
                                        id = if (count == 1) R.drawable.ic_delete else R.drawable.ic_remove_circle
                                    ),
                                    contentDescription = "Remove",
                                    tint = MaterialTheme.colorScheme.error
                                )
                            }
                            Text(
                                modifier = Modifier
                                    .padding(horizontal = 13.dp)
                                    .padding(top = 14.dp),
                                text = "$count",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Medium,
                                textAlign = TextAlign.Center
                            )
                            IconButton(
                                modifier = Modifier
                                    .size(43.dp)
                                    .padding(top = 14.dp),
                                onClick = {
                                    count++
                                    onAddToCart()
                                }
                            ) {
                                Icon(
                                    Icons.Default.AddCircle,
                                    contentDescription = "Increase",
                                    tint = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }

            // Favorite button at the top-right corner of the card
            IconButton(
                onClick = {
                    isFavorite = !isFavorite // Toggle favorite state
                    onFavoriteClick(food) // Call the favorite callback
                },
                modifier = Modifier
                    .align(Alignment.TopEnd) // Align the button to the top-right corner
            ) {
                Icon(
                    imageVector = if (isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                    contentDescription = "Favorite button",
                    tint = if (isFavorite) Color.Black else Color.Gray
                )
            }
        }
    }
}


