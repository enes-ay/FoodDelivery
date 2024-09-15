package com.example.fooddelivery.data.model

data class FoodsApiResponse(
    val success: Int,
    val yemekler: List<Yemekler>
)