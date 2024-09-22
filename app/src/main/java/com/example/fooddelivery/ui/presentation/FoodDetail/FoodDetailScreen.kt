package com.example.fooddelivery.ui.presentation.FoodDetail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.example.fooddelivery.R
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.model.Yemekler
import com.skydoves.landscapist.glide.GlideImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodDetailScreen(navController: NavController, food: Yemekler) {
    var count by remember { mutableStateOf(0) }
    val totalPrice = count*food.yemek_fiyat
    val foodDetailViewmodel : FoodDetailViewmodel = hiltViewModel()


    Scaffold (modifier = Modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { "Food Detail" })}) { paddingValues ->
        Column (modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally) {

          Column ( modifier = Modifier
              .fillMaxSize()
              .weight(3f),
              horizontalAlignment = Alignment.CenterHorizontally) {
              Text(food.yemek_adi, fontSize = 28.sp, fontWeight = FontWeight.Bold)

              GlideImage(modifier = Modifier.size(300.dp),
                  imageModel = "http://kasimadalan.pe.hu/yemekler/resimler/${food.yemek_resim_adi}",
                  contentScale = ContentScale.Crop,
              )
              Row (modifier = Modifier.fillMaxWidth(),
                  horizontalArrangement = Arrangement.SpaceEvenly) {
                  Text("${food.yemek_fiyat}₺", fontSize = 27.sp, fontWeight = FontWeight.Bold)
              }
              Row (modifier = Modifier
                  .fillMaxWidth()
                  .padding(10.dp)
                  .padding(top = 20.dp),
                  horizontalArrangement = Arrangement.Center,
                  verticalAlignment = Alignment.CenterVertically) {
                  if (count==1){
                      IconButton(
                          modifier = Modifier.size(36.dp), // Set fixed size for the button
                          onClick = {
                              count--
                              // onDelete()
                          }
                      ) {
                          Icon(
                              painter = painterResource(id = R.drawable.ic_delete),
                              contentDescription = "icon delete"
                          )
                      }
                  }
                  else if (count > 0) {
                      IconButton(
                          modifier = Modifier.size(36.dp), // Set fixed size for the button
                          onClick = {
                              count--
                              // onDelete()
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
                          //  onAddToCart()
                      }
                  ) {
                      Icon(Icons.Default.AddCircle, contentDescription = "Increase Button")
                  }
              }
          }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    modifier = Modifier
                        .defaultMinSize(minWidth = 200.dp) // Butonun minimum genişliği sabit
                        .padding(end = 10.dp), // Sağdan biraz boşluk bırakıyoruz
                    onClick = {
                        if(count>0){
                            foodDetailViewmodel.addFoodToCart(food.yemek_adi,food.yemek_resim_adi, food.yemek_fiyat, count, "enes" )
                            navController.navigate("cart") {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                        else{
                            navController.navigate("cart") {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }

                    },
                    shape = RectangleShape
                ) {
                    Text(
                        text = "Go to Cart",
                        color = Color.White,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                Text(
                    text = "Total:  ${totalPrice}₺",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }
        }

    }

}