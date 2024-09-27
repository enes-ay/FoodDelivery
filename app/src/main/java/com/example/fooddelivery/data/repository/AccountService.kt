package com.example.fooddelivery.data.repository

import com.example.fooddelivery.ui.presentation.Auth.AuthState

interface AccountService {
    suspend fun signInWithGoogle(idToken: String)

}