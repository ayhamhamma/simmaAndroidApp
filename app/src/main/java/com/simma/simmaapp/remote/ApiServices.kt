package com.vs.simma.api

import com.google.gson.JsonObject
import com.simma.simmaapp.model.CancelOrderOrItem.CancelOrderItem
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
import com.vs.simma.model.loginUserModel.LoginUserModel
import com.vs.simma.model.placeOrder.PlaceOrderResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiServices {

    @GET("api/v1/public/merchants")
    suspend fun getStoresListings(): Response<ListingModel>

    @POST("api/v1/merchants/{id}/cart")
    @Headers("Content-Type:application/json")
    suspend fun getCart(
        @Path("id") id: String,
        @Body cart: JsonObject
    ): Response<CartViewResponseModel>

    @POST("api/v1/simmaAuthentication/sendOtp")
    suspend fun sendOtp(
        @Body encryptedData: SendOtpRequestBody
    ): Response<SendOtpResponse>

    @GET("api/v1/users/exists")
    suspend fun checkUserExistence(
        @Query("phoneNumber") phoneNumber: String
    ): Response<IsUserExistModel>

    @Headers("Content-Type: application/json")
    @POST("api/v1/simmaAuthentication/authenticate")
    suspend fun verifyOtp(
        @Body encryptedData: SendOtpRequestBody,
        @Header("Authorization") authToken: String
    ): Response<SendOtpResponse>

    @Headers("Content-Type: application/json")
    @POST("api/v1/auth/authenticate")
    suspend fun getUserData(
        @Body encryptedData: SendOtpRequestBody,
        @Header("Authorization") authToken: String
    ): Response<LoginUserModel>

    @Headers("Content-Type: application/json")
    @POST("api/v1/orders")
    suspend fun placeOrder(
        @Body encryptedData: SendOtpRequestBody,
        @Header("Authorization") authToken: String,
    ): Response<PlaceOrderResponse>

    @GET("api/v1/cities")
    suspend fun getCities(): Response<CitiesModel>

    @Headers("Content-Type: application/json")
    @PATCH("api/v1/auth/me")
    suspend fun updateProfile(
        @Body data : UpdateProfileRequestBody,
        @Header("Authorization") authToken: String,
    ): Response<UpdateProfileResponse>

    @Headers("Content-Type: application/json")
    @GET("api/v1/auth/me")
    suspend fun getMyProfile(
        @Header("Authorization") authToken: String,
    ): Response<UpdateProfileResponse>

    @GET("api/v1/orders/myOrders")
    suspend fun getMyOrders(
        @Query("page") page: Int,
        @Header("Authorization") authToken: String,
        @Query("limit") limit: Int = 10
    ): Response<MyOrdersModel>

    @Headers("Content-Type: application/json")
    @POST("api/v1/inquiries")
    suspend fun sendInquiry(
        @Body data : SendInquiryRequestBody,
        @Header("Authorization") authToken: String,
    ): Response<SendInquiryModel>

    @Headers("Content-Type: application/json")
    @GET("api/v1/categories")
    suspend fun getCategories(
    ): Response<InquiriesCategories>


    @Headers("Content-Type: application/json")
    @GET("api/v1/frequentlyAskedQuestions")
    suspend fun getFrequentlyAskedQuestions(
    ): Response<FrequentlyAskedQuestionsModel>

    @Headers("Content-Type: application/json")
    @POST("api/v1/orders/applyDiscountCode")
    suspend fun applyDiscountCode(
        @Body data : ApplyDiscountModel,
        @Header("Authorization") authToken: String,
    ): Response<ApplyDiscountCodeResponse>

    @Headers("Content-Type: application/json")
    @GET("api/v1/auth/wallet")
    suspend fun getMyWallet(
        @Header("Authorization") authToken: String,
    ): Response<GetMyWalletModel>

    @Headers("Content-Type: application/json")
    @POST("api/v1/orders/{orderId}/cancel")
    suspend fun cancelOrderOrCancelOrderItem(
        @Header("Authorization") authToken: String,
        @Path("orderId") orderId : String,
        @Body itemId : CancelOrderItem
    ): Response<MyOrdersResultData>
    @Headers("Content-Type: application/json")
    @POST("api/v1/orders/{orderId}/cancel")
    suspend fun cancelOrderOrCancelOrderItem(
        @Header("Authorization") authToken: String,
        @Path("orderId") orderId : String,
    ): Response<MyOrdersResultData>

    @GET("api/v1/public/config")
    suspend fun getConfigs(
    ): Response<GetConfigsModel>

    @POST("api/v1/users/setPassword")
    suspend fun setPassword(
        @Header("Authorization") authToken: String,
        @Body data : SetPasswordRequestBody,
    ): Response<SetPasswordResponse>
    @GET("api/v1/public/advertisements")
    suspend fun advertisements():Response<AdvertismentResponse>

    @POST("api/v1/auth/authenticate")
    suspend fun login(
        @Body encryptedData: SendOtpRequestBody,
    ): Response<LoginModel>
}