package com.example.fooddelivery.ui.presentation.Profile.Address

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.fooddelivery.ui.theme.primaryColor

@Composable
fun AddressScreen(navHostController: NavHostController) {
    var addressLabel by remember { mutableStateOf("") }
    var streetName by remember { mutableStateOf("") }
    var apartmentNo by remember { mutableStateOf("") }
    var buildingNo by remember { mutableStateOf("") }

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 35.dp),
                horizontalArrangement = Arrangement.Start
            ) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.size(60.dp),
                        imageVector = Icons.AutoMirrored.Outlined.KeyboardArrowLeft,
                        contentDescription = "arrow button",
                        tint = primaryColor
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                ) {
                    TextField(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                        value = streetName,
                        onValueChange = { streetName = it },
                        label = { Text(text = "Street") })


                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Absolute.SpaceBetween
                ) {
                    TextField(modifier = Modifier
                        .width(200.dp)
                        .padding(10.dp),
                        value = buildingNo, onValueChange = { buildingNo = it },
                        label = { Text(text = "Building No.") })
                    TextField(
                        modifier = Modifier
                            .width(200.dp)
                            .padding(10.dp),
                        value = apartmentNo,
                        onValueChange = { apartmentNo = it },
                        label = { Text(text = "Apartment No") })
                }
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = addressLabel,
                    onValueChange = { addressLabel = it },
                    label = { Text(text = "Address Label") })
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = addressLabel,
                    onValueChange = { addressLabel = it },
                    label = { Text(text = "Address Label") })
                TextField(modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                    value = addressLabel,
                    onValueChange = { addressLabel = it },
                    label = { Text(text = "Address Label") })

                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(containerColor = primaryColor)
                ) {
                    Text(
                        text = "Save",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}