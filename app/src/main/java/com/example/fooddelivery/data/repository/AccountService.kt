package com.example.fooddelivery.data.repository

interface AccountService {
    suspend fun signInWithGoogle(idToken: String)

}