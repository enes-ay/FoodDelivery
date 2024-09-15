package com.example.fooddelivery.data.model

data class CartApiResponse(
    val sepet_yemekler: List<SepetYemekler>,
    val success: Int
)