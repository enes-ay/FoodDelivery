package com.example.fooddelivery.ui.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProfileScreen(modifier: Modifier = Modifier) {

    Scaffold { paddingValues ->
        Column (modifier=Modifier.fillMaxSize().padding(paddingValues)) {
            Text("Profile Screen")
        }

    }
}