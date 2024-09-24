package com.example.fooddelivery.common

import java.lang.Exception

sealed class Resource<out T>{
    data class Success<out T>(val data:T) : Resource<T>()
    data class Error(val exception: Exception) : Resource<Nothing>()
}