package com.example.fooddelivery.ui.presentation.Auth.Register

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import com.example.fooddelivery.ui.theme.primaryColor


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Register(modifier: Modifier = Modifier, navController: NavController) {

    val registerViewmodel: RegisterViewmodel = hiltViewModel()
    val authState by registerViewmodel.authState

    Scaffold(modifier = Modifier.fillMaxSize()) { paddingValues ->
        val email = remember { mutableStateOf("") }
        val password = remember { mutableStateOf("") }
        val emailError = remember { mutableStateOf<String?>(null) }
        val passwordError = remember { mutableStateOf<String?>(null) }
        var passwordVisible by remember { mutableStateOf(false) }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .wrapContentSize()
                        .padding(vertical = 20.dp),
                    text = "Register",
                    style = MaterialTheme.typography.displaySmall,
                    fontWeight = FontWeight.Medium,
                    color = primaryColor
                )

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
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(2.dp, Color.Gray),
                            shape = MaterialTheme.shapes.small
                        )
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
                    visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation() ,
                    trailingIcon = {
                        val image = if (passwordVisible)
                            Icons.Filled.Done
                        else
                            Icons.Filled.Close

                        IconButton(onClick = {
                            passwordVisible = !passwordVisible
                        }) {
                            Icon(
                                imageVector = image,
                                contentDescription = if (passwordVisible) "Hide password" else "Show password"
                            )
                        }
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Color.White,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(2.dp, Color.Gray),
                            shape = MaterialTheme.shapes.small
                        )

                )
                if (passwordError.value != null) {
                    Text(text = passwordError.value!!, color = Color.Red, fontSize = 12.sp)
                }

                // Auth State Handling
                when (authState) {
                    is AuthState.Idle ->
                        Button(
                            onClick = {
                                if (email.value.isBlank()) {
                                    emailError.value = "Email cannot be empty"
                                }
                                if (password.value.isBlank()) {
                                    passwordError.value = "Password cannot be empty"
                                }
                                if (emailError.value == null && passwordError.value == null) {
                                    registerViewmodel.signUp(email.value, password.value)
                                }
                            },
                            modifier = Modifier.width(300.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor
                            )
                        ) {
                            Text("Register", color = Color.White, fontSize = 17.sp)
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
                        Button(
                            onClick = { registerViewmodel.signUp(email.value, password.value) },
                            modifier = Modifier.width(300.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.error
                            )
                        ) {
                            Text("Retry", color = Color.White, fontSize = 17.sp)
                        }

                    }
                }
                Button(
                    onClick = {
                        navController.navigate("login"){
                            popUpTo("register")
                        }
                    },
                    modifier = Modifier.width(300.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = primaryColor
                    )
                ) {
                    Text("Cancel", color = Color.White, fontSize = 17.sp)
                }

            }
        }

    }
}