package com.example.fooddelivery.ui.presentation

import android.app.Activity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.fooddelivery.utils.Constants
import com.google.gson.Gson
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodListScreen(modifier: Modifier = Modifier) {
    val foodListViewmodel : FoodListViewmodel = hiltViewModel()
    val foodList by foodListViewmodel.foodList.observeAsState()

    LaunchedEffect (foodListViewmodel.foodList) {
        foodListViewmodel.getAllFoods()
    }
    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text(text = "Food List")})}) { paddingValues ->
        LazyVerticalGrid(modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            columns = GridCells.Fixed(2)
            ) {
            foodList?.let {
                items(it.count()){
                    val food = foodList!![it]
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            // val filmJson= Gson().toJson(film)
                            //navController.navigate("detaySayfa/$filmJson")
                        },
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        val activity = (LocalContext.current as Activity)
                        GlideImage(modifier = Modifier.size(120.dp),
                            imageModel = "${Constants.IMAGE_BASE_URL}${food.yemek_resim_adi}",
                            contentDescription = "${food.yemek_adi} image")

                        Row(modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            Text("${food.yemek_adi}", fontSize = 22.sp)
                            Text("${food.yemek_fiyat}₺", fontSize = 22.sp)
                            Button(onClick = {

                            }) {
                                Text("sepet")
                            }

                        }
                    }
                }
            }


        }

    }
}