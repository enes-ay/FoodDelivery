package com.example.fooddelivery.ui.presentation.Profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.fooddelivery.BottomBar
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import com.example.fooddelivery.ui.presentation.Auth.Login.LoginViewmodel
import com.example.fooddelivery.ui.theme.primaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    val loginViewmodel: LoginViewmodel = hiltViewModel()
    val authState by loginViewmodel.authState

    Scaffold(modifier = Modifier.fillMaxSize(),
        topBar = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TopAppBar(
                    title = {
                        Text(
                            text = "Profile",
                            fontSize = 24.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = primaryColor)
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(140.dp)
                        .background(primaryColor),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        modifier = Modifier.size(100.dp),
                        imageVector = Icons.Outlined.Person, contentDescription = "profile photo"
                    )
                    Text(
                        text = "John Doe",
                        fontSize = 25.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }

            }
        }, bottomBar = { BottomBar(navController = navController) }) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues), verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column(modifier = Modifier.fillMaxSize()) {

                ProfileItem("Update Profile")
                ProfileItem("My Orders")
                ProfileItem("My Adresses")
                ProfileItem("Settings")
                ProfileItem("FAQ")
                ProfileItem("Sign Out")

            }
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
                        navController.navigate("login") {
                            popUpTo("profile") {
                                inclusive = true
                            }
                        }
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

@Composable
fun ProfileItem(name: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
                .height(60.dp)
                .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = name, fontSize = 18.sp)
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowForward, contentDescription = "arrow icon")
        }
    }
}