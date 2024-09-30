package com.example.fooddelivery.di

import com.example.fooddelivery.data.datasource.AddressCollection
import com.example.fooddelivery.data.datasource.FavoriteCollection
import com.example.fooddelivery.data.datasource.FirebaseAddressDataSource
import com.example.fooddelivery.data.datasource.FirebaseFavDataSource
import com.example.fooddelivery.data.datasource.FoodsDataSource
import com.example.fooddelivery.data.repository.FirebaseAddressRepository
import com.example.fooddelivery.data.repository.FirebaseFavRepository
import com.example.fooddelivery.data.repository.FoodsRepository
import com.example.fooddelivery.retrofit.FoodApiUtils
import com.example.fooddelivery.retrofit.FoodsDao
import com.google.firebase.Firebase
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseAddressModule {

    @Provides
    @Singleton
    fun provideAddressRepository (addressDS: FirebaseAddressDataSource) : FirebaseAddressRepository {
        return FirebaseAddressRepository(addressDS)
    }

    @Provides
    @Singleton
    fun provideAddressDatasource(@AddressCollection collectionFavFoods: CollectionReference) : FirebaseAddressDataSource {
        return FirebaseAddressDataSource(collectionFavFoods)
    }
    @Provides
    @FavoriteCollection
    @Singleton
    fun provideFavCollectionReference() : CollectionReference {
        return  Firebase.firestore.collection("Favorites")
    }
    @Provides
    @Singleton
    @AddressCollection
    fun provideAddressReference() : CollectionReference {
        return  Firebase.firestore.collection("Addresses")
    }
}