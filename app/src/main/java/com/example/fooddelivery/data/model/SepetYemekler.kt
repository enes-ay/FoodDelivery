package com.example.fooddelivery.data.model

data class SepetYemekler(
    val kullanici_adi: String,
    val sepet_yemek_id: Int,
    val yemek_adi: String,
    val yemek_fiyat: Int,
    val yemek_resim_adi: String,
    val yemek_siparis_adet: Int
)