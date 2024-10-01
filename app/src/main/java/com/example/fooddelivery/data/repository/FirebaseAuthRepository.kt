package com.example.fooddelivery.data.repository


import com.example.fooddelivery.common.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthRepository @Inject
constructor(
    private val firebaseAuth: FirebaseAuth,
) {
    suspend fun signUp(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)

        }
    }

    suspend fun signIn(email: String, password: String): Resource<String> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            return Resource.Success(result.user?.uid.orEmpty())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signOut(): Resource<String> {
        return try {
            firebaseAuth.signOut()
            Resource.Success("User signed out successfully")
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    fun isUserLoggedIn(): Boolean = firebaseAuth.currentUser != null
}