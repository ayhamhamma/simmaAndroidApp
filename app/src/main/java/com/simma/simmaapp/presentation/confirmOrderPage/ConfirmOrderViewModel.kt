package com.simma.simmaapp.presentation.confirmOrderPage

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountModel
import com.simma.simmaapp.model.cartResponseModel.DiscountOption
import com.simma.simmaapp.presentation.theme.DarkBlue
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.APPLY_DISCOUNT
import com.simma.simmaapp.utils.Constants.CART_GRAND_TOTAL
import com.simma.simmaapp.utils.Constants.CART_PRODUCT_LIST
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY
import com.simma.simmaapp.utils.Constants.DELIVERY_DETAILED_ADDRESS
import com.simma.simmaapp.utils.Constants.DISCOUNTS_LIST
import com.simma.simmaapp.utils.Constants.DISCOUNT_CODE
import com.simma.simmaapp.utils.Constants.DISCOUNT_CODE_AMOUNT
import com.simma.simmaapp.utils.Constants.DISCOUNT_NAME
import com.simma.simmaapp.utils.Constants.IS_FREE_SHIPPING_CHECKED
import com.simma.simmaapp.utils.Constants.SHOP_ID
import com.simma.simmaapp.utils.Constants.WALLET_FREE_SHIPPING
import com.simma.simmaapp.utils.Constants.WALLET_SELECTED
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers
import com.simma.simmaapp.utils.Helpers.getSelectedCurrency
import com.simma.simmaapp.utils.Helpers.getToken
import com.simma.simmaapp.utils.Helpers.getUserId
import com.simma.simmaapp.utils.Helpers.getUserPhoneNumber
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.placeOrder.CustomerAddress
import com.vs.simma.model.placeOrder.PlaceOrderItem
import com.vs.simma.model.placeOrder.PlaceOrderNoCouponRequestBody
import com.vs.simma.model.placeOrder.PlaceOrderRequestBody
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfirmOrderViewModel @Inject constructor(
    val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    var discountButtonText by mutableStateOf("Apply")
    var showWalletFreeShipping by mutableStateOf(true)
    var discountText by mutableStateOf("")
    var discountError by mutableStateOf(false)
    var grandTotal by mutableStateOf(CART_GRAND_TOTAL)
    var discountCodeApply by mutableStateOf(false)
    var discountCodeName by mutableStateOf(DISCOUNT_NAME)
    var totalDiscountCodeAmount by mutableStateOf(DISCOUNT_CODE_AMOUNT)
    var applyColor by mutableStateOf(DarkBlue)
    private val _timerState = MutableStateFlow("45")
    val timerState: StateFlow<String> = _timerState
    var otpText = mutableStateListOf("", "", "", "", "", "")
    private var timerJob: Job? = null
    var isResendActive by mutableStateOf(false)
    var check by mutableStateOf(IS_FREE_SHIPPING_CHECKED.value)
    var discountViewList by mutableStateOf(mutableListOf<DiscountOption>())
    var globalGrandTotal = ""
    var token1 = ""
    var isBottomButtonLoading by mutableStateOf(false)

    init {
        globalGrandTotal = CART_GRAND_TOTAL
        // Logic for showing WFS at the first time
        showWalletFreeShipping = WALLET_FREE_SHIPPING > 0 && IS_FREE_SHIPPING_CHECKED.value
        IS_FREE_SHIPPING_CHECKED.combine(APPLY_DISCOUNT) { isFreeShippingChecked, applyDiscount ->
            e("ayhamCartViewModel", "this is a combine emission")
            changeCheckWFS(isFreeShippingChecked, applyDiscount)
        }.launchIn(viewModelScope)
    }


    // todo change this code to accept users phone number
    fun requestWhatsAppOtp() {
        val encryptedPhoneNumber =
            Encryption.encryptData("{\"phoneNumber\" :\"${getUserPhoneNumber(appContext)}\" }")
        viewModelScope.launch {
            repository.sendOtp(encryptedPhoneNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Helpers.setPlaceOrderToken(appContext, result.data.token)
                        Constants.PHONE_NUMBER =
                            "00${getSelectedCurrency(appContext)}" + getUserPhoneNumber(appContext).substring(4)
                    }

                    is Resource.Loading -> {
                        // nothing now
                    }

                    is Resource.Error -> {
                        showMessage(appContext, "Error Occurred")
                    }
                }
            }
        }
    }

    // Update the OTP text at a specific index
    fun updateOtpAtIndex(index: Int, value: String) {
        otpText[index] = value
    }

    fun startTimer() {
        isResendActive = false
        timerJob = viewModelScope.launch {
            for (i in 45 downTo 0) {
                if (i >= 10) {
                    _timerState.value = i.toString()
                    delay(1000)
                } else {
                    _timerState.value = "0${i}"
                }
            }
            isResendActive = true
        }
    }

    fun stopTimer() {
        timerJob?.cancel()
        timerJob = null
    }

    fun resendOTP(context: Context) {
        // Cancel the existing timer job
        timerJob?.cancel()
        // Reset OTP text
        otpText = mutableStateListOf("", "", "", "", "", "")
        // Restart the timer
        startTimer()

        val encryptedPhoneNumber =
            Encryption.encryptData("{\"phoneNumber\" :\"${getUserPhoneNumber(appContext)}\" }")
        viewModelScope.launch {
            repository.sendOtp(encryptedPhoneNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Helpers.setPlaceOrderToken(appContext, result.data.token)
                        Constants.PHONE_NUMBER =
                            "00${getSelectedCurrency(appContext)}" + getUserPhoneNumber(appContext).substring(4)
                    }

                    is Resource.Loading -> {
                        // nothing now
                    }

                    is Resource.Error -> {
                        Helpers.showMessage(appContext, "Error Occurred")
                    }
                }
            }
        }
    }

    fun applyDiscount() {
        val data = ApplyDiscountModel(
            true,
            Constants.CART_RESPONSE!!,
            discountText
        )
        val token = getToken(appContext)
        viewModelScope.launch {
            repository.applyDiscount(data,token).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data.isValid) {
                            DISCOUNT_NAME = result.data.appliedDiscount.description.en
                            DISCOUNT_CODE_AMOUNT =
                                result.data.appliedDiscount.iqdDiscountAmount.toString()
                            Constants.DISCOUNT_CODE = discountText
                            discountError = false
                            APPLY_DISCOUNT.value = true

                        } else {
                            Constants.DISCOUNT_CODE = discountText
                            discountError = true
                            APPLY_DISCOUNT.value = false
                        }
                    }

                    is Resource.Error ->
                        Unit

                    is Resource.Loading ->
                        Unit

                }
            }
        }
    }


    fun verifyOtp(context: Context, navigate: () -> Unit) {
        if (verifyOtpText()) {
            viewModelScope.launch {
                val otpTextString = otpText.joinToString(separator = "")
                Log.e("ayham", otpTextString)
                val encryptedData = Encryption.encryptData("{\"otp\":\"$otpTextString\"}")
                val token = Helpers.getPlaceOrderToken(context)
                repository.verifyOtp(encryptedData, token).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            Helpers.setPlaceOrderToken(context, result.data.token)
//                            getUserData(navigate)
                            token1 = result.data.token

                            placeOrder(navigate)
                        }

                        is Resource.Error -> {
                            // not implemented
                        }

                        is Resource.Loading -> {
                            // not implemented
                        }
                    }
                }
            }
        } else {
            showMessage(context, "Invalid Code Retry...")
        }
    }

    private fun verifyOtpText(): Boolean {
        otpText.forEach {
            if (it.isEmpty() || it.isBlank()) {
                return false
            }
        }
        return true
    }

    fun placeOrder(navigate: () -> Unit) {
        if (!isBottomButtonLoading) {
            viewModelScope.launch {
                val list = mutableListOf<PlaceOrderItem>()
                CART_PRODUCT_LIST.forEach {
                    list.add(
                        PlaceOrderItem(
                            it.color,
                            it.imageUrl,
                            it.name,
                            it.originalUnitPrice,
                            it.quantity,
                            it.size,
                            it.sizeVariants,
                            it.sku,
                            it.url
                        )
                    )
                }
                val gson = Gson()
                var json_object = ""
                if (APPLY_DISCOUNT.value) {
                    var data = PlaceOrderRequestBody(
                        WALLET_FREE_SHIPPING > 0 && IS_FREE_SHIPPING_CHECKED.value,
                        CustomerAddress(DELIVERY_CITY, DELIVERY_DETAILED_ADDRESS),
                        list.toList(),
                        SHOP_ID,
                        if (WALLET_SELECTED) "wallet" else "cashOnDelivery",
                        DISCOUNT_CODE,
                        if (WALLET_SELECTED) token1 else null
                    )
                    json_object = gson.toJson(data)
                } else {
                    var data = PlaceOrderNoCouponRequestBody(
                        WALLET_FREE_SHIPPING > 0 &&IS_FREE_SHIPPING_CHECKED.value,
                        CustomerAddress(DELIVERY_CITY, DELIVERY_DETAILED_ADDRESS),
                        list.toList(),
                        SHOP_ID,
                        if (WALLET_SELECTED) "wallet" else "cashOnDelivery",
                        if (WALLET_SELECTED) token1 else null
                    )
                    json_object = gson.toJson(data)
                }
                e("ayham", json_object)
                repository.placeOrder(
                    Encryption.encryptData(json_object, getUserId(appContext)),
                    getToken(appContext)
                ).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            showMessage("Order Added Successfully", appContext)
                            navigate()
                        }

                        is Resource.Error -> {
                            showMessage("Error", appContext)
                        }

                        is Resource.Loading -> {
                            isBottomButtonLoading = result.isLoading
                        }

                    }
                }
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    fun changeCheckWFS(isChecked: Boolean, applyDiscountCode: Boolean) {
        check = isChecked
        IS_FREE_SHIPPING_CHECKED.value = isChecked

        viewModelScope.launch {
            delay(1000)
            showWalletFreeShipping = WALLET_FREE_SHIPPING > 0 && isChecked
        }
        viewModelScope.launch {
            if (isChecked) {
                // add wallet free shipping
                // remove payment methods discount
                val newDiscounts = mutableListOf<DiscountOption>()
                e("ayham", DISCOUNTS_LIST.toList().toString())
                DISCOUNTS_LIST.forEach {
                    if (WALLET_SELECTED && it.code == "cashOnDelivery") {
                        return@forEach
                    }
                    // category can be wrong
                    if (!WALLET_SELECTED && it.code == "wallet") {
                        return@forEach
                    }
                    newDiscounts.add(it)
                }
                var total = globalGrandTotal
                newDiscounts.forEach {
                    total = (total.toDouble() + it.iqdDiscountAmount).toString()
                    Log.e("total1", total)
                }
                grandTotal = total
                Log.e("ayham", grandTotal)
                discountViewList.clear()
                discountViewList += newDiscounts


            } else {
                // remove wallet free shipping
                // remove payment method free shipping
                val newDiscounts = mutableListOf<DiscountOption>()
                e("ayham", DISCOUNTS_LIST.toString())
                DISCOUNTS_LIST.forEach {
                    if (it.code == "walletFreeShipping") {
                        return@forEach
                    }
                    if (WALLET_SELECTED && it.code == "cashOnDelivery") {
                        return@forEach
                    }
                    // category can be wrong
                    if (!WALLET_SELECTED && it.code == "wallet") {
                        return@forEach
                    }
                    newDiscounts.add(it)
                }
                var total = globalGrandTotal
                newDiscounts.forEach {
                    total = (total.toDouble() + it.iqdDiscountAmount).toString()
                }
                grandTotal = total
                discountViewList.clear()
                discountViewList += newDiscounts
            }

            discountCodeName = DISCOUNT_NAME
            totalDiscountCodeAmount = DISCOUNT_CODE_AMOUNT
            discountCodeApply = applyDiscountCode
            if (applyDiscountCode) {
                if (!grandTotal.isEmpty() &&
                    !grandTotal.isBlank() &&
                    !totalDiscountCodeAmount.isBlank() &&
                    !totalDiscountCodeAmount.isEmpty()
                ) {
                    grandTotal =
                        (grandTotal.toDouble() + totalDiscountCodeAmount.toDouble()).toString()
                }
                discountButtonText = "Remove"
                applyColor = DarkBlue
                discountText = Constants.DISCOUNT_CODE
            } else {

                discountButtonText = "Apply"
                Constants.DISCOUNT_CODE = ""
                discountText = Constants.DISCOUNT_CODE

            }

        }
    }
}