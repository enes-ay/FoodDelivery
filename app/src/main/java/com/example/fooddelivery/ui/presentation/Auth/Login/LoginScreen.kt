package com.example.fooddelivery.ui.presentation.Auth.Login

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login(navController: NavController) {
    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val email = remember { mutableStateOf("") }
            val password = remember { mutableStateOf("") }
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
                        text = "Login",
                        style = MaterialTheme.typography.displaySmall,
                        color = MaterialTheme.colorScheme.primary
                    )

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

                    when (authState) {
                        is AuthState.Idle -> Button(
                            onClick = {
                                if (email.value.isBlank()) {
                                    emailError.value = "Email cannot be empty"
                                }
                                if (password.value.isBlank()) {
                                    passwordError.value = "Password cannot be empty"
                                }
                                if (emailError.value == null && passwordError.value == null) {
                                    loginViewmodel.signIn(email.value, password.value)
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Text("Login", color = Color.White)
                        }

                        is AuthState.Loading -> CircularProgressIndicator()

                        is AuthState.Authenticated -> {
                            Text(text = "Login Successful!", color = MaterialTheme.colorScheme.primary)
                            navController.navigate("foodList") {
                                popUpTo("login") { inclusive = true }
                            }
                        }

                        is AuthState.Error -> {
                            Text(
                                text = (authState as AuthState.Error).message,
                                color = Color.Red,
                                fontSize = 14.sp
                            )
                            Button(
                                onClick = { loginViewmodel.signIn(email.value, password.value) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.error
                                )
                            ) {
                                Text("Retry", color = Color.White)
                            }
                        }
                    }

                    Text(
                        text = "Don't have an account? Create here!",
                        modifier = Modifier
                            .padding(10.dp)
                            .clickable { navController.navigate("register") },
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.secondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}
