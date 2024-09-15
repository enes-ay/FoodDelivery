package com.example.fooddelivery.di

import com.example.fooddelivery.data.datasource.FoodsDataSource
import com.example.fooddelivery.data.repository.FoodsRepository
import com.example.fooddelivery.retrofit.FoodApiUtils
import com.example.fooddelivery.retrofit.FoodsDao
import com.example.fooddelivery.retrofit.RetrofitClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideFoodsRepository (foodDS: FoodsDataSource) : FoodsRepository{
        return FoodsRepository(foodDS)
    }

    @Provides
    @Singleton
    fun provideDataSource (foodsDao: FoodsDao) : FoodsDataSource{
        return FoodsDataSource(foodsDao)
    }

    @Provides
    @Singleton
    fun provideFoodsDao () : FoodsDao{
        return FoodApiUtils.getFoodsDao()
    }

}