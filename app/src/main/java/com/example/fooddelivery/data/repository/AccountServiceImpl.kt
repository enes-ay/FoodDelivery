package com.example.fooddelivery.data.repository

import android.util.Log
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AccountServiceImpl @Inject constructor(var firebaseAuth: FirebaseAuth) : AccountService {

    override suspend fun signInWithGoogle(idToken: String) {
        val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(firebaseCredential).await()
    }
}

//override suspend fun signInWithGoogle(idToken: String): AuthState {
//    val firebaseCredential = GoogleAuthProvider.getCredential(idToken, null)
//    val response = firebaseAuth.signInWithCredential(firebaseCredential)
//    if (response.isSuccessful){
//        return AuthState.Authenticated
//    }
//    else if (response.isCanceled){
//        return AuthState.Error("Cancelled")
//    }
//    else{
//        return AuthState.Error("Error ${response.exception}")
//    }
//}
//}

