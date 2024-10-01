package com.example.fooddelivery.ui.presentation.Profile.AddAddress

import ResultState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.repository.FirebaseAddressRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddAddressViewmodel @Inject constructor(val addressRepository: FirebaseAddressRepository) :
    ViewModel() {

    private val _addressState = MutableStateFlow<ResultState>(ResultState.Idle)
    val addressState: StateFlow<ResultState> = _addressState

    fun saveAddress(
        street: String,
        buildingNo: String,
        apartmentNo: String,
        addressLabel: String,
        fullName: String,
        phoneNumber: String
    ) {
        _addressState.value = ResultState.Loading
        viewModelScope.launch {
            val result = addressRepository.saveAddress(
                street,
                buildingNo,
                apartmentNo,
                addressLabel,
                fullName,
                phoneNumber
            )
            if (result.isSuccess) {
                _addressState.value = ResultState.Success
            } else {
                _addressState.value =
                    ResultState.Error(result.exceptionOrNull()?.message ?: "Unknown error")
            }
        }
    }
}