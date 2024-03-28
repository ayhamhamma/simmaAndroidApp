package com.simma.simmaapp.remote

import com.simma.simmaapp.model.advertismentResponse.AdvertismentResponse
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountCodeResponse
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountModel
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
import com.simma.simmaapp.model.citiesModel.CitiesModel
import com.simma.simmaapp.model.frequentlyAskedModel.FrequentlyAskedQuestionsModel
import com.simma.simmaapp.model.getConfigsModel.GetConfigsModel
import com.simma.simmaapp.model.getInquiryCategoriesModel.InquiriesCategories
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.simma.simmaapp.model.isUserExist.IsUserExistModel
import com.simma.simmaapp.model.loginModel.LoginModel
import com.simma.simmaapp.model.myOrdersModel.MyOrdersModel
import com.simma.simmaapp.model.myOrdersModel.MyOrdersResultData
import com.simma.simmaapp.model.passwordResponse.SetPasswordResponse
import com.simma.simmaapp.model.sendInquiry.SendInquiryModel
import com.simma.simmaapp.model.sendInquiry.SendInquiryRequestBody
import com.simma.simmaapp.model.setPasswordRequestBody.SetPasswordRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileResponse
import com.vs.simma.model.auth.SendOtpRequestBody
import com.vs.simma.model.auth.SendOtpResponse
import com.vs.simma.model.listingModel.ListingModel
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.loginUserModel.LoginUserModel
import com.vs.simma.model.placeOrder.PlaceOrderResponse
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.Body

interface Repository {
    suspend fun getStoresListings(): Flow<Resource<ListingModel>>

    suspend fun sendOtp(encryptedData: String): Flow<Resource<SendOtpResponse>>

    suspend fun checkUserExistence(phoneNumber: String): Flow<Resource<IsUserExistModel>>

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
        data : ApplyDiscountModel,
        tokenString: String
    ): Flow<Resource<ApplyDiscountCodeResponse>>

    suspend fun getMyWallet(
        token: String
    ): Flow<Resource<GetMyWalletModel>>

    suspend fun cancelOrderOrCancelOrderItem(
        token: String,
        orderId : String,
        itemId : String? = null
    ): Flow<Resource<MyOrdersResultData>>
    suspend fun getConfigs(
    ): Flow<Resource<GetConfigsModel>>
    suspend fun setPassword(
        password : SetPasswordRequestBody,
        token : String
    ): Flow<Resource<SetPasswordResponse>>

    suspend fun getAdvertisements(): Flow<Resource<AdvertismentResponse>>
    suspend fun login(
        encryptedData: SendOtpRequestBody,
    ): Flow<Resource<LoginModel>>


}