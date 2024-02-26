package com.simma.simmaapp.utils

import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.model.cartResponseModel.DiscountOption
import com.simma.simmaapp.model.cartResponseModel.Item
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.vs.simma.model.listingModel.PaymentMethod
import kotlinx.coroutines.flow.MutableStateFlow

object Constants {
    var selectedUrl=""
    var CART_URL = ""
    var MERCHANT_ID = ""
    var PHONE_NUMBER = ""
    var SHOP_ID = ""
    var EXTRACTION_DATA = mapOf<String,Any>()
    var PAYMENT_METHODS = listOf<PaymentMethod>()
    var DELIVERY_CITY = ""
    var DELIVERY_CITY_NAME = ""
    var DELIVERY_DETAILED_ADDRESS = ""
    var DELIVERY_FEES = ""
    var CART_NUMBER_OF_ITEMS = ""
    var CART_TOTAL = ""
    var CART_SHIPPING_FEES = ""
    var CART_GRAND_TOTAL = ""
    var CART_PRODUCT_LIST = listOf<Item>()
    var WALLET_SELECTED = true
    var CART_RESPONSE : CartViewResponseModel? = null
    var DISCOUNTS_LIST = listOf<DiscountOption>()

    // state flow for reactively update this state from another screen
    var APPLY_DISCOUNT = MutableStateFlow(false)
    var DISCOUNT_NAME = ""
    var DISCOUNT_CODE_AMOUNT = ""
    var WALLET_FREE_SHIPPING = 0
    var IS_FREE_SHIPPING_CHECKED = MutableStateFlow(true)
    var USER_WALLET : GetMyWalletModel? = null
    var DISCOUNT_CODE : String = ""
}