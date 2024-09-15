package com.example.fooddelivery.ui.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodListViewmodel @Inject constructor(val foodRepo: FoodsRepository): ViewModel() {
    val foodList = MutableLiveData<List<Yemekler>>()

    init {
        getAllFoods()
    }
    fun getAllFoods(){
        CoroutineScope(Dispatchers.Main).launch {
            foodList.value = foodRepo.getAllFoods()
        }
    }

    fun addFoodToCart(yemek_adi:String, yemek_resim_adi: String, yemek_fiyat: Int,
                      yemek_siparis_adet: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            foodRepo.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
        }
    }
}