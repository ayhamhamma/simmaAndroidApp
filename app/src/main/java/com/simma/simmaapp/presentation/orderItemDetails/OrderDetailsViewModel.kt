package com.simma.simmaapp.presentation.orderItemDetails

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.model.myOrdersModel.DiscountModel
import com.simma.simmaapp.model.myOrdersModel.Item
import com.simma.simmaapp.model.myOrdersModel.PaymentMethod
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.ORDER_CHANGED
import com.simma.simmaapp.utils.Constants.SELECTED_ORDER
import com.simma.simmaapp.utils.Constants.SELECTED_ORDERS_CHANGE
import com.simma.simmaapp.utils.Helpers.getToken
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderDetailsViewModel @Inject constructor(
    val repository: Repository,
    @ApplicationContext val appContext: Context
) : ViewModel() {
    var paymentMethodsList: List<PaymentMethod> = listOf()
    var cartUrl: String = ""
    var merchantId: String = ""
    var extractionCode: String = ""
    var orderId: String = ""
    var externalStatus by mutableStateOf(
        ""
    )
    var orderNumber by mutableStateOf(
        ""
    )
    var date by mutableStateOf(
        ""
    )
    var paymentMethod by mutableStateOf(
        ""
    )
    var isExpanded by mutableStateOf(
        false
    )
    var isCancelOrderPopUpVisible by mutableStateOf(
        false
    )
    var showCancelItemPopUp by mutableStateOf(
        false
    )

    //    val onBackPress : (()->Unit)? = null,
//    onCancelOrderPress: (() -> Unit)? = null,
    var itemsList by mutableStateOf(
        listOf<Item>()
    )

    //    onItemClick : ((String) ->Unit)? = null,
//    onItemDelete : ((String) -> Unit)? = null,
//    onExpandChange : (()->Unit)? = null,
    var numberOfItem by mutableStateOf(
        0
    )
    var totalInsight by mutableStateOf(
        0.0
    )
    var shipping by mutableStateOf(
        0.0
    )
    var grandTotal by mutableStateOf(
        0.0
    )
    var discounts by mutableStateOf(
        listOf<DiscountModel>()
    )

    init {
        viewModelScope.launch {
            SELECTED_ORDERS_CHANGE.collect {
                SELECTED_ORDER?.let {
                    orderNumber = it.orderNumber
                    date = it.updatedAt
                    paymentMethod = it.paymentMethod
                    itemsList = it.items
                    numberOfItem = it.items.size
                    totalInsight = it.paymentDetails.IQDTotal
                    shipping = it.paymentDetails.IQDShippingCost
                    grandTotal = it.paymentDetails.IQDGrandTotal
                    discounts = it.discounts
                    cartUrl = it.merchant.cartUrl
                    merchantId = it.merchant.id
                    extractionCode = it.merchant.cartCode
                    paymentMethodsList = it.merchant.paymentMethods
                    orderId = it._id
                    externalStatus = it.externalStatus
                    it.merchant.apply {
                        Constants.DETAILS_COVER_IMAGE = coverImage.originalUrl?:""
                        Constants.DETAILS_IMAGE = image.originalUrl ?: ""
                        Constants.DETAILS_NAME = name.en
                        Constants.DETAILS_DESCRIPTION = ""
                        Constants.DETAILS_COUNTRY = country
                        Constants.DETAILS_MIN_DAYS = minimumDaysToDeliver.toString()
                        Constants.DETAILS_MAX_DAYS = maximumDaysToDeliver.toString()
                        Constants.DETAILS_ITEM_PRICED = extraDeliveryPerItem.toString()
                        Constants.DETAILS_DEALS = deals.filter { it.isActive }.map { it.description.en}
                    }

                }
            }
        }


    }

    fun cancelOrderOrCancelItem(
        itemId: String? = null,
    ) {
        viewModelScope.launch {
            repository.cancelOrderOrCancelOrderItem(getToken(appContext), orderId = orderId, itemId)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            SELECTED_ORDER = result.data
                            SELECTED_ORDERS_CHANGE.update {
                                !it
                            }
                            ORDER_CHANGED.update {
                                !it
                            }
                            isCancelOrderPopUpVisible =false
                            showCancelItemPopUp = false
                            if(itemId != null){
                                HomeActivity.showToast(true,"Order item removed successfully")
                            }else{
                                HomeActivity.showToast(true,"Order cancelled successfully.")
                            }
                        }
                        is Resource.Error ->{
                            showCancelItemPopUp = false
                            isCancelOrderPopUpVisible =false
                            HomeActivity.showToast(false,result.errorMessage)
                        }
                        is Resource.Loading ->{

                        }
                    }
                }
        }

    }


}