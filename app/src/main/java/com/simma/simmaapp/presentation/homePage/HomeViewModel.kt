package com.simma.simmaapp.presentation.homePage

import android.content.Context
import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.CATEGORIES_LIST
import com.simma.simmaapp.utils.Constants.MERCHANTS_LIST
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.listingModel.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    // Use a mutable state variable for storesList
    var storesList by mutableStateOf<List<Result>>(emptyList())
        private set
    var bannerList by mutableStateOf(
        listOf<com.simma.simmaapp.model.advertismentResponse.Result>()
    )

    init {
        getStoreListings()
        getBanner()
    }

    private fun getBanner() {
        viewModelScope.launch {
            repository.getAdvertisements().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        bannerList = result.data.results
                    }

                    is Resource.Loading -> {
                    }

                    is Resource.Error -> {
                        HomeActivity.showToast(false, "Unexpected Error")
                    }

                }
            }
        }
    }

    // Handel API response
    private fun getStoreListings() {
        viewModelScope.launch {
            repository.getStoresListings().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        storesList = result.data.results
                        MERCHANTS_LIST = result.data.results
                        CATEGORIES_LIST.forEach { category ->
                            if (category.id == "all") {
                                MERCHANTS_LIST.forEach {
                                    category.merchants.add(it)
                                }

                            } else {
                                MERCHANTS_LIST.filter {
                                    it.subCategorys.contains(category.id)
                                }.forEach {
                                    category.merchants.add(it)
                                }

                            }

                        }
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

    fun navigate(
        item: com.simma.simmaapp.model.advertismentResponse.Result,
        navController: NavController,
        context: Context
    ) {
        if (item.actionType == "store") {
            startStoreNavigation(
                item,
                navController
            )
        } else {
            startMenuItemNavigation(
                item, navController,
                context
            )
        }
    }

    private fun startMenuItemNavigation(
        item: com.simma.simmaapp.model.advertismentResponse.Result,
        navController: NavController,
        context: Context
    ) {
        when (item.actionId) {
            "profile" -> {
                navController.navigate(HomeScreens.ProfileScreen.route)
            }

            "wallet" -> {
                navController.navigate(HomeScreens.WalletScreen.route)
            }

            "orders" -> {
                navController.navigate(HomeScreens.MyOrdersScreen.route)
            }

            "referal" -> {
                navController.navigate(HomeScreens.ReferAndEarnScreen.route)
            }

            "language" -> {
                navController.navigate(HomeScreens.UserProfileOptions.route)
            }

            "inquiry" -> {
                navController.navigate(HomeScreens.SendInquiryScreen.route)
            }

            "contactus" -> {
                navController.navigate(HomeScreens.UserProfileOptions.route)
            }

            "faq" -> {
                navController.navigate(HomeScreens.FrequentlyAskedQuestions.route)
            }

            "login" -> {
                context.startActivity(Intent(context, LoginActivity::class.java))
            }
        }

    }

    private fun startStoreNavigation(
        item: com.simma.simmaapp.model.advertismentResponse.Result,
        navController: NavController
    ) {
        val itemData = MERCHANTS_LIST.find {
            it.id == item.actionId
        }!!
        val url = URLEncoder.encode(itemData.url, StandardCharsets.UTF_8.toString())
        val cartUrl =
            URLEncoder.encode(itemData.cartUrl, StandardCharsets.UTF_8.toString())

        Constants.EXTRACTION_CODE = itemData.cartCode
        Constants.DETAILS_COVER_IMAGE = itemData.coverImage.originalUrl
        Constants.DETAILS_IMAGE = itemData.image?.originalUrl ?: ""
        Constants.DETAILS_NAME = itemData.name.en
        Constants.DETAILS_DESCRIPTION = ""
        Constants.DETAILS_COUNTRY = itemData.country
        Constants.DETAILS_MIN_DAYS = itemData.minimumDaysToDeliver.toString()
        Constants.DETAILS_MAX_DAYS = itemData.maximumDaysToDeliver.toString()
        Constants.DETAILS_ITEM_PRICED = itemData.extraDeliveryPerItem.toString()
        Constants.DETAILS_DEALS =
            itemData.deals.filter { it.isActive }.map { it.description.en }

        navController.navigate(
            HomeScreens.WebViewScreen.withArgs(
                url,
                cartUrl,
                itemData.id
            )
        )
        Constants.PAYMENT_METHODS = itemData.paymentMethods

    }
}