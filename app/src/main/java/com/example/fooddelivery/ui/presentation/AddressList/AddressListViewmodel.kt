package com.example.fooddelivery.ui.presentation.AddressList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fooddelivery.data.model.Address
import com.example.fooddelivery.data.repository.FirebaseAddressRepository
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddressListViewmodel @Inject constructor(val addressRepository: FirebaseAddressRepository) : ViewModel() {
    private val _addressList = MutableStateFlow<List<Address>>(emptyList())
    val addressList: StateFlow<List<Address>> = _addressList


    fun getAddresses() {
        viewModelScope.launch {
            val addresses = addressRepository.getAddresses()
            _addressList.value = addresses
        }
    }

}