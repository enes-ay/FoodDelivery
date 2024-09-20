package com.example.fooddelivery.ui.presentation.Cart

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(val foodsRepository: FoodsRepository): ViewModel() {
    val foods = MutableLiveData<List<SepetYemekler>>()

    suspend fun getCartFoods(kullanici_adi:String) = withContext(Dispatchers.Main){
         foods.value = foodsRepository.getFoodsInCart(kullanici_adi)
    }


    fun deleteFoodFromCart(sepet_yemek_id: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
            foods.value = foodsRepository.getFoodsInCart(kullanici_adi)
        }
    }
}