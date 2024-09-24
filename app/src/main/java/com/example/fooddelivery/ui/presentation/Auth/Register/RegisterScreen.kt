package com.example.fooddelivery.ui.presentation.Auth.Register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fooddelivery.ui.presentation.Auth.AuthState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(modifier: Modifier = Modifier, navController: NavController) {

    val registerViewmodel:RegisterViewmodel = hiltViewModel()
    val authState by registerViewmodel.authState

    Scaffold (modifier = Modifier.fillMaxSize()) { paddingValues ->

        val name = remember { mutableStateOf("") }
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val nameError = remember { mutableStateOf<String?>(null) }
        val emailError = remember { mutableStateOf<String?>(null) }
        val passwordError = remember { mutableStateOf<String?>(null) }


        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Register",
                    style = MaterialTheme.typography.displaySmall,
                    color = MaterialTheme.colorScheme.primary
                )
                TextField(
                    value = name.value,
                    onValueChange = {
                        name.value = it
                        nameError.value = null
                    },
                    label = { Text("Name") },
                    isError = nameError.value != null,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF2F2F2)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                )
                if (nameError.value != null) {
                    Text(text = nameError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Email Field
                TextField(
                    value = email.value,
                    onValueChange = {
                        email.value = it
                        emailError.value = null
                    },
                    label = { Text("Email") },
                    isError = emailError.value != null,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF2F2F2)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                if (emailError.value != null) {
                    Text(text = emailError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Password Field
                TextField(
                    value = password.value,
                    onValueChange = {
                        password.value = it
                        passwordError.value = null
                    },
                    label = { Text("Password") },
                    isError = passwordError.value != null,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color(0xFFF2F2F2)
                    ),
                    modifier = Modifier
                        .fillMaxWidth()

                )
                if (passwordError.value != null) {
                    Text(text = passwordError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Auth State Handling
                when (authState) {
                    is AuthState.Idle ->
                        Button(
                        onClick = {
                            if (name.value.isBlank()) {
                                nameError.value = "Name cannot be empty"
                            }
                            if (email.value.isBlank()) {
                                emailError.value = "Email cannot be empty"
                            }
                            if (password.value.isBlank()) {
                                passwordError.value = "Password cannot be empty"
                            }
                            if (emailError.value == null && passwordError.value == null && nameError.value == null) {
                                registerViewmodel.signUp(email.value, password.value)
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("Register", color = Color.White)
                    }

                    is AuthState.Loading -> CircularProgressIndicator()

                    is AuthState.Authenticated -> {
                        Text(text = "Registration is Successful!")
                        navController.navigate("foodList")
                    }

                    is AuthState.Error -> {
                        Text(text = (authState as AuthState.Error).message, color = Color.Red)
//                        Button(onClick = {
//                            registerViewmodel.signUp(email.value, password.value)
//                        }) {
//                            Text("Register")
//                        }
                        Text(
                            text = (authState as AuthState.Error).message,
                            color = Color.Red,
                            fontSize = 14.sp
                        )
                        Button(
                            onClick = { registerViewmodel.signUp(email.value, password.value) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Retry", color = Color.White)
                        }

                    }
                }
            }
        }

    }
}