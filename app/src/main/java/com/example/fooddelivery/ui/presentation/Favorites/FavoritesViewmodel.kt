package com.example.fooddelivery.ui.presentation.Favorites

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.data.model.YemeklerFirebase
import com.example.fooddelivery.data.repository.FirebaseFavRepository
import com.example.fooddelivery.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewmodel @Inject constructor(var favFoodsRepository: FirebaseFavRepository): ViewModel() {
    var favList = MutableLiveData<List<YemeklerFirebase>>()

    fun saveFavFood(yemekAdi : String, yemekFiyat: Int, yemekId: String, yemekResimAdi: String){
        favFoodsRepository.saveFavFood(yemekAdi, yemekFiyat, yemekId, yemekResimAdi)
    }
    fun getFavFoods(){
        favList = favFoodsRepository.getFavFoods()
    }

    fun deleteFavFood(){

    }
}