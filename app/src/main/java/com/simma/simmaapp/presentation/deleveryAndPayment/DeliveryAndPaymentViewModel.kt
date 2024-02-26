package com.simma.simmaapp.presentation.deleveryAndPayment

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.model.citiesModel.CitiesModelItem
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.CART_GRAND_TOTAL
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY_NAME
import com.simma.simmaapp.utils.Constants.DELIVERY_DETAILED_ADDRESS
import com.simma.simmaapp.utils.Constants.DELIVERY_FEES
import com.simma.simmaapp.utils.Constants.DISCOUNTS_LIST
import com.simma.simmaapp.utils.Constants.USER_WALLET
import com.simma.simmaapp.utils.Constants.WALLET_FREE_SHIPPING
import com.simma.simmaapp.utils.Constants.WALLET_SELECTED
import com.simma.simmaapp.utils.Helpers.getToken
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryAndPaymentViewModel @Inject constructor(
    val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {


    var freeDelivery by mutableStateOf(false)
    var citiesList by mutableStateOf<List<CitiesModelItem>>(emptyList())
    var isDetailedAddressError by mutableStateOf(false)
    var cityDeliveryFees by mutableStateOf("0")
    var deliveryFees by mutableStateOf(
        "0"
    )
    var deliveryCity = ""
    var deliveryCityName by mutableStateOf("City")

    var deliveryAddress by mutableStateOf("")
    var walletBalance by mutableStateOf("")
    var isWalletFrozen by mutableStateOf(false)
    var showPopup by mutableStateOf(false)
    var walletDiscountShow by mutableStateOf(false)
    var walletDiscountAmount by mutableStateOf("")

    var codDiscountShow by mutableStateOf(false)
    var codDiscountAmount by mutableStateOf("")

    init {
        getCities()
        getMyWallet()
        USER_WALLET?.apply {
            walletBalance = balance.toString()
            isWalletFrozen = isFreezed
        }

        // logic for showing discount on payment methods
        showPaymentMethodDiscounts()
    }

    private fun showPaymentMethodDiscounts() {
        val paymentMethodDiscounts = DISCOUNTS_LIST.filter {
            it.category == "paymentMethods"
        }
        paymentMethodDiscounts.forEach {
            if(it.code == "cashOnDelivery"){
                codDiscountShow = true
                codDiscountAmount = it.iqdDiscountAmount.toString()
            }
            if(it.code == "wallet"){
                walletDiscountShow = true
                walletDiscountAmount = it.iqdDiscountAmount.toString()
            }
        }
    }

    private fun getMyWallet() {
        viewModelScope.launch {
            repository.getMyWallet(getToken(context = appContext)).collect { result ->
                when (result) {
                    is Resource.Success -> {

                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            repository.getCities().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        citiesList = result.data
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        Unit
                    }
                }
            }
        }
    }

    fun calculateDelivery() {
        // this sets the delivery fees to 0 if the payment method have free delivery
        if (freeDelivery) {
            deliveryFees = "0"
        } else {
            deliveryFees = cityDeliveryFees
        }
    }

    fun saveDeliveryData(): Boolean {

        if (deliveryCity.isEmpty()) {
            Toast.makeText(
                appContext,
                "Please choose City",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        if (deliveryAddress.isEmpty()) {
            isDetailedAddressError = true
            return false
        }

        if (WALLET_SELECTED && isWalletFrozen) {
            showPopup = true
            return false
        }
        if (WALLET_SELECTED && walletBalance.toDouble() < CART_GRAND_TOTAL.toDouble()) {
            Toast.makeText(
                appContext,
                "You don't have enough credit in your wallet. Please choose a different payment method or charge your wallet",
                Toast.LENGTH_LONG
            ).show()
            return false
        }

        DELIVERY_CITY = deliveryCity
        DELIVERY_CITY_NAME = deliveryCityName
        DELIVERY_DETAILED_ADDRESS = deliveryAddress
        DELIVERY_FEES = deliveryFees
        return true
    }
}