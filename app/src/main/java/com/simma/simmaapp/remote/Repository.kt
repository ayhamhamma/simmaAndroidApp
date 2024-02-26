package com.simma.simmaapp.remote

import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountCodeResponse
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountModel
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.model.citiesModel.CitiesModel
import com.simma.simmaapp.model.frequentlyAskedModel.FrequentlyAskedQuestionsModel
import com.simma.simmaapp.model.getInquiryCategoriesModel.InquiriesCategories
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.simma.simmaapp.model.myOrdersModel.MyOrdersModel
import com.simma.simmaapp.model.sendInquiry.SendInquiryModel
import com.simma.simmaapp.model.sendInquiry.SendInquiryRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileResponse
import com.vs.simma.model.auth.SendOtpResponse
import com.vs.simma.model.listingModel.ListingModel
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.loginUserModel.LoginUserModel
import com.vs.simma.model.placeOrder.PlaceOrderResponse
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun getStoresListings(): Flow<Resource<ListingModel>>

    suspend fun sendOtp(encryptedData: String): Flow<Resource<SendOtpResponse>>

    suspend fun checkUserExistence(phoneNumber: String): Flow<Resource<Boolean>>

    suspend fun verifyOtp(
        encryptedData: String,
        tokenString: String
    ): Flow<Resource<SendOtpResponse>>

    suspend fun getCart(
        id: String,
        cart: Map<String, Any>
    ): Flow<Resource<CartViewResponseModel>>

    suspend fun getUserData(
        encryptedData: String,
        tokenString: String
    ): Flow<Resource<LoginUserModel>>

    suspend fun getCities(): Flow<Resource<CitiesModel>>

    suspend fun placeOrder(
        data : String,
        tokenString: String
    ): Flow<Resource<PlaceOrderResponse>>

    suspend fun updateProfile(
        data : UpdateProfileRequestBody,
        token :String
    ): Flow<Resource<UpdateProfileResponse>>
    suspend fun getMyProfile(
        token :String
    ): Flow<Resource<UpdateProfileResponse>>

    suspend fun getMyOrders(
        page : Int,
        tokenString: String
    ): Flow<Resource<MyOrdersModel>>

    suspend fun sendInquiry(
        data : SendInquiryRequestBody,
        tokenString: String
    ): Flow<Resource<SendInquiryModel>>

    suspend fun getInquiryCategories(
    ): Flow<Resource<InquiriesCategories>>

    suspend fun getFrequentlyAskedQuestions(
    ): Flow<Resource<FrequentlyAskedQuestionsModel>>

    suspend fun applyDiscount(
        data : ApplyDiscountModel
    ): Flow<Resource<ApplyDiscountCodeResponse>>

    suspend fun getMyWallet(
        token: String
    ): Flow<Resource<GetMyWalletModel>>

}