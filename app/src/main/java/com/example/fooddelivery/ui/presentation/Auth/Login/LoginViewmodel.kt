package com.example.fooddelivery.ui.presentation.Auth.Login


import android.content.Intent
import android.credentials.*
import android.util.Log

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.credentials.*
import androidx.credentials.Credential
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.BuildConfig
import com.example.fooddelivery.R
import com.example.fooddelivery.common.Resource
import com.example.fooddelivery.data.repository.AccountService
import com.example.fooddelivery.data.repository.FirebaseAuthRepository
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential.Companion.TYPE_GOOGLE_ID_TOKEN_CREDENTIAL
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val authRepository: FirebaseAuthRepository,
    val accountService: AccountService
) :
    ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState
    val userLoggedIn = mutableStateOf(false)

    init {
        userLoggedIn.value = authRepository.isUserLoggedIn()
    }

    fun signInWithGoogle(credential: Credential, navigate: (String)-> Unit) {
        viewModelScope.launch {
            try {
                if (credential is CustomCredential && credential.type == TYPE_GOOGLE_ID_TOKEN_CREDENTIAL) {
                    val googleIdTokenCredential = GoogleIdTokenCredential.createFrom(credential.data)
                    accountService.signInWithGoogle(googleIdTokenCredential.idToken)
                    navigate("foodList")
                } else {
                    navigate("login")
                }
            }catch (e:Exception){
                Log.e("credential",e.message.toString())
            }
        }
    }


    fun signIn(email: String, password: String) = viewModelScope.launch {
        if (email.isEmpty() || password.isEmpty()) {
            _authState.value = AuthState.Error("Email or Password cannot be null")
        } else {
            val result = authRepository.signIn(email, password)
            _authState.value = when (result) {
                is Resource.Success -> AuthState.Authenticated
                is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
            }
        }
    }

    fun signOut() = viewModelScope.launch {
        val result = authRepository.signOut()
        _authState.value = when (result) {
            is Resource.Success -> {
                userLoggedIn.value = false
                AuthState.Idle
            }

            is Resource.Error -> AuthState.Error(result.exception.message ?: "Unknown error")
        }
    }

}