package com.simma.simmaapp.utils

import com.simma.simmaapp.R
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.model.cartResponseModel.DiscountOption
import com.simma.simmaapp.model.cartResponseModel.Item
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.simma.simmaapp.model.myOrdersModel.MyOrdersResultData
import com.simma.simmaapp.presentation.categoriesPage.Category
import com.vs.simma.model.listingModel.PaymentMethod
import com.vs.simma.model.listingModel.Result
import kotlinx.coroutines.flow.MutableStateFlow

object Constants {
    var MERCHANTS_LIST = listOf<Result>()
    var selectedUrl = ""
    var CART_URL = ""
    var MERCHANT_ID = ""
    var PHONE_NUMBER = ""
    var PHONE_NUMBER_RESEND = ""
    var SHOP_ID = ""

    //    var EXTRACTION_DATA = MutableStateFlow(mapOf<String,Any>())
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
    var WALLET_SELECTED = false
    var CART_RESPONSE: CartViewResponseModel? = null
    var DISCOUNTS_LIST = listOf<DiscountOption>()
    var NAVIGATE_TO_CART = {

    }

    // state flow for reactively update this state from another screen
    var APPLY_DISCOUNT = MutableStateFlow(false)
    var DISCOUNT_NAME = ""
    var DISCOUNT_CODE_AMOUNT = ""
    var WALLET_FREE_SHIPPING = 0
    var IS_FREE_SHIPPING_CHECKED = MutableStateFlow(true)
    var USER_WALLET: GetMyWalletModel? = null
    var DISCOUNT_CODE: String = ""
    var EXTRACTION_CODE = ""
    var SELECTED_ORDER: MyOrdersResultData? = null
    var SELECTED_ORDERS_CHANGE = MutableStateFlow(true)
    var ORDER_CHANGED = MutableStateFlow(true)
    var IS_WEB_VIEW_SHOWING = false
    var ON_WEB_VIEW_BACK = {}
    var DETAILS_COVER_IMAGE = ""
    var DETAILS_IMAGE = ""
    var DETAILS_NAME = ""
    var DETAILS_DESCRIPTION = ""
    var DETAILS_COUNTRY: String? = ""
    var DETAILS_MIN_DAYS = ""
    var DETAILS_MAX_DAYS = ""
    var DETAILS_ITEM_PRICED = ""
    var DETAILS_DEALS = listOf<String>()
    var NAVIGATION_PHONE_NUMBER: String? = null
    var CATEGORIES_LIST = mutableListOf(
        Category(
            id = "all",
            name = "All Stores",
            icon = R.drawable.all_stores_icon,
            merchants = mutableListOf()
        ),
        Category(
            id = "Apparel",
            name = "Apparel",
            icon = R.drawable.apperal,
            merchants = mutableListOf()
        ),
        Category(
            id = "Kids",
            name = "Kids",
            icon = R.drawable.baby_icon,
            merchants = mutableListOf()
        ),
        Category(
            id = "Beauty",
            name = "Beauty",
            icon = R.drawable.beauty_icon,
            merchants = mutableListOf()
        ),
        Category(
            id = "Sports",
            name = "Sports",
            icon = R.drawable.fitness_icon,
            merchants = mutableListOf()
        ),
        Category(
            id = "Perfumes",
            name = "Perfumes",
            icon = R.drawable.apperal,
            merchants = mutableListOf()
        ),
        Category(
            id = "Accessories",
            name = "Accessories",
            icon = R.drawable.accessories_icon,
            merchants = mutableListOf()
        ) ,
        Category(
            id = "Shoes",
            name = "Shoes",
            icon = R.drawable.apperal,
            merchants = mutableListOf()
        )
    )


}