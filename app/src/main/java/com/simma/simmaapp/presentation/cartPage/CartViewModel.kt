package com.simma.simmaapp.presentation.cartPage

import android.content.Context
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountModel
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.model.cartResponseModel.DiscountOption
import com.simma.simmaapp.model.cartResponseModel.Item
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.simma.simmaapp.presentation.theme.DarkBlue
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.APPLY_DISCOUNT
import com.simma.simmaapp.utils.Constants.CART_GRAND_TOTAL
import com.simma.simmaapp.utils.Constants.CART_NUMBER_OF_ITEMS
import com.simma.simmaapp.utils.Constants.CART_PRODUCT_LIST
import com.simma.simmaapp.utils.Constants.CART_RESPONSE
import com.simma.simmaapp.utils.Constants.CART_SHIPPING_FEES
import com.simma.simmaapp.utils.Constants.CART_TOTAL
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY
import com.simma.simmaapp.utils.Constants.DELIVERY_CITY_NAME
import com.simma.simmaapp.utils.Constants.DELIVERY_DETAILED_ADDRESS
import com.simma.simmaapp.utils.Constants.DELIVERY_FEES
import com.simma.simmaapp.utils.Constants.DISCOUNTS_LIST
import com.simma.simmaapp.utils.Constants.DISCOUNT_CODE
import com.simma.simmaapp.utils.Constants.DISCOUNT_CODE_AMOUNT
import com.simma.simmaapp.utils.Constants.DISCOUNT_NAME
import com.simma.simmaapp.utils.Constants.EXTRACTION_DATA
import com.simma.simmaapp.utils.Constants.IS_FREE_SHIPPING_CHECKED
import com.simma.simmaapp.utils.Constants.PAYMENT_METHODS
import com.simma.simmaapp.utils.Constants.SHOP_ID
import com.simma.simmaapp.utils.Constants.USER_WALLET
import com.simma.simmaapp.utils.Constants.WALLET_FREE_SHIPPING
import com.simma.simmaapp.utils.Constants.WALLET_SELECTED
import com.simma.simmaapp.utils.Helpers
import com.vs.simma.model.listingModel.PaymentMethod
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    var discountButtonText by mutableStateOf("Apply")
    var listState by mutableStateOf<List<Item>>(emptyList())
    var numberOfItems by mutableStateOf("")
    var totalInsight by mutableStateOf("")
    var shipping by mutableStateOf("")
    var grandTotal by mutableStateOf("")
    var applyColor by mutableStateOf(DarkBlue)
    var expanded by mutableStateOf(false)
    var discountError by mutableStateOf(false)
    var discountText by mutableStateOf("")
    var discountCodeApply by mutableStateOf(false)
    var discountCodeName by mutableStateOf(DISCOUNT_NAME)
    var totalDiscountCodeAmount by mutableStateOf(DISCOUNT_CODE_AMOUNT)
    var discountsList = mutableStateListOf<DiscountOption>()
    var globalGrandTotal = ""
    var isDataFetched = MutableStateFlow(false)


    init {
        getData(SHOP_ID, EXTRACTION_DATA)
        getMyWallet()

        // state flow combine operator is used to combine two flows so that any emission from any flow will trigger
        // this three line of code
        IS_FREE_SHIPPING_CHECKED.combine(APPLY_DISCOUNT) { isFreeShippingChecked, applyDiscount ->
            e("ayhamCartViewModel", "this is a combine emission")
            DiscountStateModel(
                isFreeShippingChecked,
                applyDiscount
            )
        }.combine(isDataFetched) { data, isDataFitched ->
            calculateGrandTotal(data.isFreeShippingChecked, data.applyDiscount)
        }.launchIn(viewModelScope)


    }

    private fun getMyWallet() {
        viewModelScope.launch {
            repository.getMyWallet(Helpers.getToken(context = appContext)).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // calculation for wallet free shipping count
                        WALLET_FREE_SHIPPING = result.data.promotions.find {
                            it.category == "freeShipping"
                        }?.value ?: 0

                        IS_FREE_SHIPPING_CHECKED.value = WALLET_FREE_SHIPPING > 0

                        USER_WALLET = result.data
                    }

                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getData(id: String, json: Map<String, Any>) {
        viewModelScope.launch {
            repository.getCart(id, json).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        CART_RESPONSE = result.data
                        listState = result.data.items
                        numberOfItems = listState.size.toString()
                        totalInsight = result.data.paymentDetails.IQDTotal.toString()
                        shipping = result.data.paymentDetails.IQDShippingCost.toString()
                        globalGrandTotal = result.data.paymentDetails.IQDGrandTotal.toString()

                        // just make sure the list is the same as the API
                        DISCOUNTS_LIST = result.data.discountOptions
                        isDataFetched.value = true

                    }

                    is Resource.Error ->
                        Unit

                    is Resource.Loading ->
                        Unit

                }
            }
        }
    }

    fun applyDiscount() {
        val data = ApplyDiscountModel(
            true,
            CART_RESPONSE!!,
            discountText
        )
        viewModelScope.launch {
            repository.applyDiscount(data).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data.isValid) {
                            DISCOUNT_NAME = result.data.appliedDiscount.description.en
                            DISCOUNT_CODE_AMOUNT =
                                result.data.appliedDiscount.iqdDiscountAmount.toString()
                            DISCOUNT_CODE = discountText
                            discountError = false
                            APPLY_DISCOUNT.value = true

                        } else {
                            DISCOUNT_CODE = discountText
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

    fun saveCartData() {
        CART_NUMBER_OF_ITEMS = numberOfItems
        CART_TOTAL = totalInsight
        CART_SHIPPING_FEES = shipping
        CART_GRAND_TOTAL = globalGrandTotal
        CART_PRODUCT_LIST = listState
        DISCOUNT_NAME = discountCodeName
        DISCOUNT_CODE_AMOUNT = totalDiscountCodeAmount
    }



    private fun calculateGrandTotal(isChecked: Boolean, applyDiscountCode: Boolean) {
        viewModelScope.launch {
            if (isChecked) {
                // add wallet free shipping
                // remove payment methods discount
                val newDiscounts = mutableListOf<DiscountOption>()
                e("ayham", DISCOUNTS_LIST.toString() )
                DISCOUNTS_LIST.forEach {
                    if (it.category == "paymentMethods") {
                        return@forEach
                    }
                    newDiscounts.add(it)
                }
                e("ayhamCartViewModel", globalGrandTotal)
                var total = globalGrandTotal
                newDiscounts.forEach {
                    total = (total.toDouble() + it.iqdDiscountAmount).toString()
                    e("total1", total)
                }
                grandTotal = total
                e("ayham", grandTotal)
                discountsList.clear()
                discountsList += newDiscounts


            } else {
                // remove wallet free shipping
                // remove payment method free shipping
                val newDiscounts = mutableListOf<DiscountOption>()
                e("ayham", DISCOUNTS_LIST.toString() )
                DISCOUNTS_LIST.forEach {
                    if (it.code == "walletFreeShipping") {
                        return@forEach
                    }
                    if (it.category == "paymentMethods") {
                        return@forEach
                    }
                    newDiscounts.add(it)
                }
                var total = globalGrandTotal
                newDiscounts.forEach {
                    total = (total.toDouble() + it.iqdDiscountAmount).toString()
                }
                grandTotal = total
                discountsList.clear()
                discountsList += newDiscounts
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
                discountText = DISCOUNT_CODE
            } else {

                discountButtonText = "Apply"
                DISCOUNT_CODE = ""
                discountText = DISCOUNT_CODE

            }
        }
    }

    override fun onCleared() {
        // clear flow to manage leaking
        DISCOUNT_NAME = ""
        DISCOUNT_CODE_AMOUNT = ""
        APPLY_DISCOUNT.value = false
        EXTRACTION_DATA = mapOf<String,Any>()
//        PAYMENT_METHODS = listOf<PaymentMethod>()
        DELIVERY_CITY = ""
        DELIVERY_CITY_NAME = ""
        DELIVERY_DETAILED_ADDRESS = ""
        DELIVERY_FEES = ""
        CART_NUMBER_OF_ITEMS = ""
        CART_TOTAL = ""
        CART_SHIPPING_FEES = ""
        CART_GRAND_TOTAL = ""
        CART_PRODUCT_LIST = listOf<Item>()
        WALLET_SELECTED = true
        CART_RESPONSE = null
        DISCOUNTS_LIST = listOf<DiscountOption>()
        APPLY_DISCOUNT = MutableStateFlow(false)
        DISCOUNT_NAME = ""
        DISCOUNT_CODE_AMOUNT = ""
        WALLET_FREE_SHIPPING = 0
        IS_FREE_SHIPPING_CHECKED = MutableStateFlow(true)
        USER_WALLET  = null
        DISCOUNT_CODE = ""
        super.onCleared()
    }

}


data class DiscountStateModel(
    val isFreeShippingChecked: Boolean,
    val applyDiscount: Boolean,
)