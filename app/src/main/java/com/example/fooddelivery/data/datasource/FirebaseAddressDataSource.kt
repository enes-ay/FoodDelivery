package com.example.fooddelivery.data.datasource

import com.example.fooddelivery.data.model.Address
import com.google.firebase.firestore.CollectionReference
import javax.inject.Inject

class FirebaseAddressDataSource @Inject constructor (@AddressCollection val addressReference: CollectionReference) {

    fun saveAddress(
        street: String, buildingNo: String,
        apartmentNo: String,
        addressLabel: String,
        fullName: String,
        phoneNumber: String,
    ) {
        val newAddress = Address(street, buildingNo, apartmentNo, addressLabel, fullName, phoneNumber)
        addressReference.document().set(newAddress)
    }
}