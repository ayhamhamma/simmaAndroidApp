package com.simma.simmaapp.repository

import android.util.Log
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountCodeResponse
import com.simma.simmaapp.model.applyDiscountCode.ApplyDiscountModel
import com.simma.simmaapp.model.cartResponseModel.CartViewResponseModel
//import com.simma.simmaapp.model.MyOrdersModel.MyOrdersModel
import com.simma.simmaapp.model.citiesModel.CitiesModel
import com.simma.simmaapp.model.frequentlyAskedModel.FrequentlyAskedQuestionsModel
import com.simma.simmaapp.model.getInquiryCategoriesModel.InquiriesCategories
import com.simma.simmaapp.model.getWallet.GetMyWalletModel
import com.simma.simmaapp.model.myOrdersModel.MyOrdersModel
import com.simma.simmaapp.model.sendInquiry.SendInquiryModel
import com.simma.simmaapp.model.sendInquiry.SendInquiryRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileRequestBody
import com.simma.simmaapp.model.updateProfile.UpdateProfileResponse
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Helpers.convertMapToJsonObject
import com.vs.simma.api.ApiServices
import com.vs.simma.model.auth.SendOtpRequestBody
import com.vs.simma.model.auth.SendOtpResponse
import com.vs.simma.model.listingModel.ListingModel
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.loginUserModel.LoginUserModel
import com.vs.simma.model.placeOrder.PlaceOrderResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class RepositoryImp @Inject constructor(
    private val services: ApiServices
) : Repository {

    override suspend fun sendOtp(encryptedData: String): Flow<Resource<SendOtpResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val body = SendOtpRequestBody(encryptedData)
                val result = services.sendOtp(body)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun checkUserExistence(phoneNumber: String): Flow<Resource<Boolean>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val result = services.checkUserExistence(phoneNumber)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun verifyOtp(
        encryptedData: String,
        tokenString: String
    ): Flow<Resource<SendOtpResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val body = SendOtpRequestBody(encryptedData)
                val token = "Bearer ${tokenString}"

                val result = services.verifyOtp(body, token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getStoresListings(): Flow<Resource<ListingModel>> {
        return flow {
            try {
                emit(Resource.Loading(true))
                val result = services.getStoresListings()
                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }

    }
    override suspend fun getCart(
        id: String,
        cart: Map<String, Any>
    ): Flow<Resource<CartViewResponseModel>> {
        return flow {
            try {
                emit(Resource.Loading(true))

                val result = services.getCart(id, convertMapToJsonObject(cart))
                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }

    }

    override suspend fun getUserData(
        encryptedData: String,
        tokenString: String
    ): Flow<Resource<LoginUserModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val body = SendOtpRequestBody(encryptedData)
                val token = "Bearer ${tokenString}"

                val result = services.getUserData(body, token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                    Log.e("ayhamTOKEN", result.body()!!.token.accessToken)
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getCities(): Flow<Resource<CitiesModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val result = services.getCities()
                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }
    override suspend fun placeOrder(
        data : String,
        tokenString: String
    ): Flow<Resource<PlaceOrderResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val token = "Bearer ${tokenString}"

                val result = services.placeOrder(SendOtpRequestBody(data),token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun updateProfile(data: UpdateProfileRequestBody,tokenString: String): Flow<Resource<UpdateProfileResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val token = "Bearer ${tokenString}"
                val result = services.updateProfile(data,token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }
    override suspend fun getMyProfile(tokenString: String): Flow<Resource<UpdateProfileResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val token = "Bearer ${tokenString}"
                val result = services.getMyProfile(token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMyOrders(page: Int,tokenString: String): Flow<Resource<MyOrdersModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))

                val token = "Bearer ${tokenString}"
                val result = services.getMyOrders(page,token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun sendInquiry(
        data: SendInquiryRequestBody,
        tokenString: String
    ): Flow<Resource<SendInquiryModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))

                val token = "Bearer ${tokenString}"
                val result = services.sendInquiry(data,token)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getInquiryCategories(): Flow<Resource<InquiriesCategories>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))

                val result = services.getCategories()

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getFrequentlyAskedQuestions(): Flow<Resource<FrequentlyAskedQuestionsModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))

                val result = services.getFrequentlyAskedQuestions()

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun applyDiscount(data: ApplyDiscountModel): Flow<Resource<ApplyDiscountCodeResponse>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))

                val result = services.applyDiscountCode(data)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }

    override suspend fun getMyWallet(token: String): Flow<Resource<GetMyWalletModel>> {
        return flow {
            try {
                emit(Resource.Loading(isLoading = true))
                val tokenString = "Bearer ${token}"
                val result = services.getMyWallet(tokenString)

                if (result.isSuccessful) {
                    emit(Resource.Success(data = result.body()!!))
                } else {
                    emit(Resource.Error("Could not get data from API"))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Could not get data from API"))
            }
            emit(Resource.Loading(false))
        }
    }


}