package com.example.fooddelivery.retrofit

import com.example.fooddelivery.data.model.AddFoodResponse
import com.example.fooddelivery.data.model.CartApiResponse
import com.example.fooddelivery.data.model.FoodsApiResponse
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.POST

interface FoodsDao {

    @GET("tumYemekleriGetir.php")
    suspend fun getAllFoods() : FoodsApiResponse

    @GET("yemekler/sepettekiYemekleriGetir.php")
    suspend fun getFoodsInCart() : CartApiResponse

    @POST("yemekler/sepeteYemekEkle.php")
    suspend fun addFoodToCart(
        @Field("yemek_adi")  yemek_adi: String,
        @Field("yemek_resim_adi")  yemek_resim_adi : String,
        @Field("yemek_fiyat")  yemek_fiyat: Int,
        @Field("yemek_siparis_adet")  yemek_siparis_adet: Int,
        @Field("kullanici_adi")  kullaniic_adi: String,

    ) : AddFoodResponse

    @POST("yemekler/sepettenYemekSil.php")
    suspend fun deleteFood(
        @Field("sepet_yemek_id") sepet_yemek_id : Int,
        @Field("kullanici_adi") kullanici_adi : String
    )
}