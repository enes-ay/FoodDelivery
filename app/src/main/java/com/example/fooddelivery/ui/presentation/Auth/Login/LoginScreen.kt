package com.example.fooddelivery.ui.presentation.Auth.Login

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.Credential
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.exceptions.GetCredentialException
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.fooddelivery.BuildConfig
import com.example.fooddelivery.R
import com.example.fooddelivery.ui.theme.primaryColor
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

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
                    .background(Color.White, shape = RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(20.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        modifier = Modifier
                            .wrapContentSize()
                            .padding(vertical = 20.dp),
                        text = "Login",
                        style = MaterialTheme.typography.displaySmall,
                        fontWeight = FontWeight.Medium,
                        color = primaryColor
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

                    TextField(
                        value = password.value,
                        singleLine = true,
                        onValueChange = {
                            password.value = it
                            passwordError.value = null
                        },
                        label = { Text("Password") },
                        isError = passwordError.value != null,
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
                            modifier = Modifier.width(300.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                            )
                        ) {
                            Text("Login", fontSize = 17.sp, fontWeight = FontWeight.Medium)
                        }

                        is AuthState.Loading -> CircularProgressIndicator()

                        is AuthState.Authenticated -> {
                            Text(
                                text = "Login Successful!",
                                color = MaterialTheme.colorScheme.primary
                            )
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
                    GoogleLoginButton(
                        onGetCredentialResponse = { credential ->
                            loginViewmodel.signInWithGoogle(credential, navigate = {
                                navController.navigate(it) {
                                    popUpTo("login") {
                                        inclusive = true
                                    }
                                }
                            })
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun GoogleLoginButton(
    onGetCredentialResponse: (Credential) -> Unit,
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val credentialManager = CredentialManager.create(context)

    Button(modifier = Modifier.width(200.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = primaryColor
        ),
        onClick = {
            val googleIdOption = GetGoogleIdOption.Builder()
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(BuildConfig.GOOGLE_CLIENT_ID)
                .build()
            val request = GetCredentialRequest.Builder()
                .addCredentialOption(googleIdOption)
                .build()

            coroutineScope.launch {
                try {
                    val result = credentialManager.getCredential(
                        request = request,
                        context = context
                    )
                    onGetCredentialResponse(result.credential)
                    Log.e("credentialError", result.credential.toString())
                } catch (e: GetCredentialException) {
                    Log.e("credentialError", e.message.orEmpty())
                }
            }

        }) {
        Text("Sign in with Google", fontSize = 16.sp, fontWeight = FontWeight.Medium)
    }

}
