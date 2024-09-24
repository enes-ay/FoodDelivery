package com.example.fooddelivery.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.fooddelivery.data.datasource.FirebaseFavDataSource
import com.example.fooddelivery.data.model.Yemekler

class FirebaseFavRepository (var firebaseFavDataSource: FirebaseFavDataSource) {

    fun saveFavFood(yemekAdi : String, yemekFiyat: Int, yemekId: Int, yemekResimAdi: String) = firebaseFavDataSource.saveFavFood(yemekAdi,yemekFiyat, yemekId, yemekResimAdi )
    fun getFavFoods() : MutableLiveData<List<Yemekler>> = firebaseFavDataSource.getFavFoods()
}