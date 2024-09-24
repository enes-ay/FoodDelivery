package com.example.fooddelivery.ui.presentation.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import com.example.fooddelivery.ui.presentation.Auth.Login.LoginViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { Text(text = "Profile") })
        }, bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(vertical = 20.dp), verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text("Welcome!", fontSize = 25.sp)
            Image(
                modifier = Modifier.size(150.dp),

                imageVector = Icons.Default.Person, contentDescription = "profile photo"
            )
            Text(
                modifier = Modifier.clickable {
                    showDialog = true
                },
                text = "Sign out", color = Color.Red, fontSize = 26.sp
            )
            SignOutDialog(
                showDialog = showDialog,
                onDismiss = { showDialog = false },
                onConfirm = {
                    loginViewmodel.signOut()
                    if (authState is AuthState.Idle) {
                        navController.navigate("login")
                    }
                    showDialog = false
                },
            )
        }

    }
}

@Composable
fun SignOutDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Are you sure want to log out?") },
            confirmButton = {
                TextButton(onClick = { onConfirm() }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = { onDismiss() }) {
                    Text("No")
                }
            }
        )
    }
}