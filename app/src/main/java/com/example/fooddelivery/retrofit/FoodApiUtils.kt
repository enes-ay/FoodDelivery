package com.example.fooddelivery.retrofit

import com.example.fooddelivery.utils.Constants

class FoodApiUtils {

    companion object{

        fun getFoodsDao() : FoodsDao{
            return RetrofitClient.getClient(Constants.BASE_URL)
                .create(FoodsDao::class.java)
        }
    }
}