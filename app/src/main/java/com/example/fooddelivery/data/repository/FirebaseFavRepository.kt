package com.example.fooddelivery.data.repository

import androidx.lifecycle.MutableLiveData
import com.example.fooddelivery.data.datasource.FirebaseFavDataSource
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.data.model.YemeklerFirebase

class FirebaseFavRepository (var firebaseFavDataSource: FirebaseFavDataSource) {

    fun saveFavFood(yemekAdi : String, yemekFiyat: Int, yemekId: String, yemekResimAdi: String) = firebaseFavDataSource.saveFavFood(yemekAdi,yemekFiyat, yemekId, yemekResimAdi )
    fun getFavFoods() : MutableLiveData<List<YemeklerFirebase>> = firebaseFavDataSource.getFavFoods()
}