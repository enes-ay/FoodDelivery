package com.example.fooddelivery.ui.presentation.Profile.Address

import androidx.lifecycle.ViewModel
import com.example.fooddelivery.data.repository.FirebaseAddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddressViewmodel @Inject constructor(val addressRepository: FirebaseAddressRepository) :
    ViewModel() {
    fun saveAddres(
        street: String, buildingNo: String,
        apartmentNo: String,
        addressLabel: String,
        fullName: String,
        phoneNumber: String,
    ) {
        addressRepository.saveAddress(
            street,
            buildingNo,
            apartmentNo,
            addressLabel,
            fullName,
            phoneNumber
        )
    }
}