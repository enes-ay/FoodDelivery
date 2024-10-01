package com.example.fooddelivery.data.repository

import com.example.fooddelivery.data.datasource.FirebaseAddressDataSource
import com.example.fooddelivery.data.datasource.FirebaseFavDataSource
import javax.inject.Inject

class FirebaseAddressRepository @Inject constructor(var firebaseAddressDataSource: FirebaseAddressDataSource) {
    suspend fun saveAddress(
        street: String,
        buildingNo: String,
        apartmentNo: String,
        addressLabel: String,
        fullName: String,
        phoneNumber: String
    ): Result<Unit> {
        return firebaseAddressDataSource.saveAddress(street, buildingNo, apartmentNo, addressLabel, fullName, phoneNumber)
    }
}