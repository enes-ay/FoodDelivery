package com.example.fooddelivery.di

import com.example.fooddelivery.data.datasource.FirebaseFavDataSource
import com.example.fooddelivery.data.repository.FirebaseFavRepository
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.firestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FirebaseFavModule {

    @Provides
    @Singleton
    fun provideFavFoodRepository(firebaseFavDataSource: FirebaseFavDataSource): FirebaseFavRepository {
        return  FirebaseFavRepository(firebaseFavDataSource)
    }
    @Provides
    @Singleton
    fun provideFavFoodDatasource(collectionFavFoods: CollectionReference) : FirebaseFavDataSource {

        return FirebaseFavDataSource(collectionFavFoods)
    }
    @Provides
    @Singleton
    fun provideFavCollectionReference() : CollectionReference {

        return  Firebase.firestore.collection("Favorites")
    }

    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

}