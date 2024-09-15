package com.example.fooddelivery.data.repository

import com.example.fooddelivery.data.datasource.FoodsDataSource

class FoodsRepository (var foodDS: FoodsDataSource) {

    suspend fun getAllFoods() = foodDS.getAllFoods()
    suspend fun addFoodToCart(yemek_adi:String, yemek_resim_adi: String, yemek_fiyat: Int,
                        yemek_siparis_adet: Int, kullanici_adi: String) = foodDS.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
    suspend fun deleteFoodFromCart(sepet_yemek_id : Int, kullanici_adi: String) = foodDS.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
    suspend fun getFoodsInCart(kullanici_adi: String) = foodDS.getFoodsInCart(kullanici_adi)

}