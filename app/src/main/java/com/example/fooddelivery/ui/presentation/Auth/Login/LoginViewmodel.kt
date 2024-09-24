package com.example.fooddelivery.ui.presentation.Auth.Login


import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.common.Resource
import com.example.fooddelivery.data.repository.FirebaseAuthRepository
import com.example.fooddelivery.ui.presentation.Auth.AuthState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(private val authRepository: FirebaseAuthRepository) :ViewModel() {

    private val _authState = mutableStateOf<AuthState>(AuthState.Idle)
    val authState: State<AuthState> = _authState
    val userLoggedIn = mutableStateOf(false)

    init {
        userLoggedIn.value= authRepository.isUserLoggedIn()
    }

    fun signIn(email:String, password:String) = viewModelScope.launch{
        if(email.isEmpty() || password.isEmpty()){
            _authState.value = AuthState.Error("Email or Password cannot be null")
        }
        else{
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