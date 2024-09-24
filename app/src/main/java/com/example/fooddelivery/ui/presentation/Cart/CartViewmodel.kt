package com.example.fooddelivery.ui.presentation.Cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.data.repository.FoodsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.EOFException
import javax.inject.Inject

@HiltViewModel
class CartViewmodel @Inject constructor(val foodsRepository: FoodsRepository): ViewModel() {
    val foods = MutableLiveData<List<SepetYemekler>>()

    init {
        getCartFoods("enes")
    }

    fun getCartFoods(kullanici_adi:String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                foods.value = foodsRepository.getFoodsInCart(kullanici_adi)
            }
            catch (e:EOFException){
                foods.value = listOf()
                Log.e("eof", "hata eof")
            }
        }
    }
    fun addFoodToCart(yemek_adi:String, yemek_resim_adi: String, yemek_fiyat: Int,
                      yemek_siparis_adet: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
            getCartFoods("enes")
        }
    }


    fun deleteFoodFromCart(sepet_yemek_id: Int, kullanici_adi: String){
        CoroutineScope(Dispatchers.Main).launch {
            foodsRepository.deleteFoodFromCart(sepet_yemek_id, kullanici_adi)
            getCartFoods("enes")
        }
    }
}