package com.example.fooddelivery.data.datasource

import com.example.fooddelivery.data.model.AddFoodResponse
import com.example.fooddelivery.data.model.CartApiResponse
import com.example.fooddelivery.data.model.SepetYemekler
import com.example.fooddelivery.data.model.Yemekler
import com.example.fooddelivery.retrofit.FoodsDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FoodsDataSource(var foodsDao: FoodsDao) {

    suspend fun getAllFoods() :  List<Yemekler> = withContext(Dispatchers.IO){
        return@withContext foodsDao.getAllFoods().yemekler
    }
    suspend fun addFoodToCart(yemek_adi:String, yemek_resim_adi: String, yemek_fiyat: Int,
                              yemek_siparis_adet: Int, kullanici_adi: String) :  AddFoodResponse = withContext(Dispatchers.IO) {
        return@withContext foodsDao.addFoodToCart(yemek_adi, yemek_resim_adi, yemek_fiyat, yemek_siparis_adet, kullanici_adi)
    }
    suspend fun deleteFoodFromCart(sepet_yemek_id:Int, kullanici_adi: String)  = withContext(Dispatchers.IO){ foodsDao.deleteFood(sepet_yemek_id, kullanici_adi)
        foodsDao.deleteFood(sepet_yemek_id, kullanici_adi)
    }
    suspend fun getFoodsInCart(kullanici_adi: String) :  List<SepetYemekler> = withContext(Dispatchers.IO){
        return@withContext foodsDao.getFoodsInCart(kullanici_adi).sepet_yemekler
    }
}