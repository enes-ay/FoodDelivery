package com.example.fooddelivery.ui.presentation.AddAddress

import ResultState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material3.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fooddelivery.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddressScreen(navController: NavHostController) {
    var addressLabel by remember { mutableStateOf("") }
    var streetName by remember { mutableStateOf("") }
    var apartmentNo by remember { mutableStateOf("") }
    var buildingNo by remember { mutableStateOf("") }
    var phoneNumber by remember { mutableStateOf("") }
    var fullname by remember { mutableStateOf("") }
    val addressLabelError = remember { mutableStateOf<String?>(null) }
    val streetNameError = remember { mutableStateOf<String?>(null) }
    val apartmentNoError = remember { mutableStateOf<String?>(null) }
    val phoneNumberError = remember { mutableStateOf<String?>(null) }
    val fullnameError = remember { mutableStateOf<String?>(null) }

    val addAddressViewmodel: AddAddressViewmodel = hiltViewModel()
    val state by addAddressViewmodel.addressState.collectAsState()


    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp, vertical = 35.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    navController.navigate("profile") {
                        popUpTo("address") {
                            inclusive = true
                        }
                    }
                }) {
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
                    .weight(4f)
                    .padding(10.dp, vertical = 5.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    modifier = Modifier.padding(bottom = 20.dp),
                    text = "Add new address",
                    fontSize = 23.sp,
                    fontWeight = FontWeight.Medium
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .wrapContentHeight()
                ) {
                    TextField(
                        value = streetName,
                        onValueChange = { streetName = it },
                        label = { Text(text = "Street \\ Avenue") },
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            unfocusedLabelColor = Color.Gray
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(
                                BorderStroke(1.dp, Color.Gray),
                                shape = MaterialTheme.shapes.small
                            )
                    )
                    if (streetNameError.value != null) {
                        Text(
                            text = streetNameError.value!!, color = Color.Red, fontSize = 12.sp
                        )
                    }

                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 8.dp),
                    ) {
                        TextField(
                            value = buildingNo,
                            onValueChange = { buildingNo = it },
                            label = { Text(text = "Building No.") },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Color.Gray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(1.dp, Color.Gray),
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(end = 8.dp),
                    ) {
                        TextField(
                            value = apartmentNo,
                            onValueChange = { apartmentNo = it },
                            label = { Text(text = "Apartment No.") },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                unfocusedLabelColor = Color.Gray
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    BorderStroke(1.dp, Color.Gray),
                                    shape = MaterialTheme.shapes.small
                                )
                        )
                        if (apartmentNoError.value != null) {
                            Text(
                                text = apartmentNoError.value!!, color = Color.Red, fontSize = 12.sp
                            )
                        }
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            BorderStroke(1.dp, Color.Gray),
                            shape = MaterialTheme.shapes.small
                        ),
                    value = addressLabel,
                    onValueChange = { addressLabel = it },
                    label = { Text(text = "Address Label") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Gray
                    )

                )
                if (addressLabelError.value != null) {
                    Text(
                        text = addressLabelError.value!!, color = Color.Red, fontSize = 12.sp
                    )
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            BorderStroke(1.dp, Color.Gray),
                            shape = MaterialTheme.shapes.small
                        ),
                    value = fullname,
                    onValueChange = { fullname = it },
                    label = { Text(text = "Fullname") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Gray
                    ),
                )

                if (fullnameError.value != null) {
                    Text(
                        text = fullnameError.value!!, color = Color.Red, fontSize = 12.sp
                    )
                }

                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .border(
                            BorderStroke(1.dp, Color.Gray),
                            shape = MaterialTheme.shapes.small
                        ),
                    value = phoneNumber,
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        unfocusedLabelColor = Color.Gray
                    ),
                    onValueChange = {
                        if (it.length <= 11 && it.all { char -> char.isDigit() }) {
                            phoneNumber = it
                        }
                    },

                    label = { Text(text = "Phone Number") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1
                )
                if (phoneNumberError.value != null) {
                    Text(
                        text = phoneNumberError.value!!, color = Color.Red, fontSize = 12.sp
                    )
                }
                Button(
                    onClick = {
                        if (addressLabel.isBlank()) {
                            addressLabelError.value = "Adress label cannot be empty"
                        }
                        if (streetName.isBlank()) {
                            streetNameError.value = "Street field cannot be empty"
                        }
                        if (apartmentNo.isBlank()) {
                            apartmentNoError.value = "Apartment No. cannot be empty"
                        }
                        if (fullname.isBlank()) {
                            fullnameError.value = "Fullname cannot be empty"
                        }
                        if (phoneNumber.isBlank()) {
                            phoneNumberError.value = "Phone number cannot be empty"
                        }
                        if (addressLabelError.value == null && streetNameError.value == null && apartmentNoError.value == null && phoneNumberError.value == null
                        ) {
                            addAddressViewmodel.saveAddress(
                                streetName,
                                buildingNo,
                                apartmentNo,
                                addressLabel,
                                fullname,
                                phoneNumber
                            )
                        }
                    }, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp), colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor,
                    )
                ) {
                    Text(
                        "Save", fontSize = 17.sp, fontWeight = FontWeight.Medium,
                        color = Color.White
                    )
                }
                when (state) {
                    is ResultState.Loading -> {
                        CircularProgressIndicator()
                    }

                    is ResultState.Success -> {
                        navController.navigate("addressList"){
                            popUpTo("addAddress")
                        }
                    }

                    is ResultState.Error -> {
                        Text(
                            text = "Error: ${
                                (state as ResultState
                                .Error).message
                            }"
                        )
                    }
                    else ->{
                    }
                }

            }
        }
    }
}