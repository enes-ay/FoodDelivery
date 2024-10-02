package com.example.fooddelivery.ui.presentation.Favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
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
import com.example.fooddelivery.data.model.YemeklerFirebase
import com.example.fooddelivery.ui.theme.primaryColor
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Favorites(navController: NavHostController) {
    var favoritesViewmodel: FavoritesViewmodel = hiltViewModel()
    val favList by favoritesViewmodel.favList.observeAsState()
    
    LaunchedEffect(true) {
        favoritesViewmodel.getFavFoods()
    }

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.favoritesLabel),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
            )        },
        bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (favList != null) {
                items(favList!!.count()) {
                    val favFood = favList!![it]
                    CardItem(food = favFood)
                }
            }

        }

    }

}
@Composable
fun CardItem(food: YemeklerFirebase,
){

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp) // Add padding around the card
            .shadow(8.dp, RoundedCornerShape(16.dp)), // Add shadow and round corners
        shape = RoundedCornerShape(16.dp), // Rounded corners
        // elevation = 8.dp, // Elevation for the card shadow
    ) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .clickable { }
            .background(Color.White)
            .height(120.dp)
            .padding(vertical = 4.dp, horizontal = 10.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1.5f)
            ) {

                GlideImage(modifier = Modifier
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

                androidx.compose.material.Text(
                    text = food.yemek_adi,
                    fontWeight = FontWeight.Bold,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )

                androidx.compose.material.Text(
                    text = "${food.yemek_fiyat}₺",
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
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 12.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Icon(imageVector = Icons.Outlined.Favorite, contentDescription = "arrow icon", tint = primaryColor)
                }
            }
        }
    }

}