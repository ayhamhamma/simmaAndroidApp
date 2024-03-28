package com.simma.simmaapp.presentation.registrationView

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants.NAVIGATION_PHONE_NUMBER
import com.simma.simmaapp.utils.Constants.PHONE_NUMBER
import com.simma.simmaapp.utils.Constants.PHONE_NUMBER_RESEND
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers.getSelectedCurrency
import com.simma.simmaapp.utils.Helpers.setToken
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: Repository,
    @ApplicationContext private val appContext : Context
    ) : ViewModel() {

    var referralText by mutableStateOf("")
    var phoneNumber by mutableStateOf(if (NAVIGATION_PHONE_NUMBER != null) NAVIGATION_PHONE_NUMBER!! else "")
    var textMessageSelected by mutableStateOf(false)
    var whatsAppSelected by mutableStateOf(false)
    var telegramSelected by mutableStateOf(false)
    var isWhatsAppEnabled by mutableStateOf(false)
    var isSMSEnabled by mutableStateOf(false)
    var isTelegramEnabled by mutableStateOf(false)
    var isBiometricEnabled by mutableStateOf(false)
    var isForget =false

    init {
        getConfigs()
    }

    private fun checkUserExistence(onBackPressed: () -> Unit, navigate: () -> Unit) {
        viewModelScope.launch {
            repository.checkUserExistence(phoneNumber = "+${getSelectedCurrency(appContext)}" + phoneNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        if (result.data.profileCompleted) {
                            LoginActivity.showToast(
                                false,
                                "You're already registered, so please log in instead of signing up."
                            )
                            onBackPressed()
                        } else {
                            requestOtp(navigate)
                        }
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
    }


    fun onButtonClick(context: Context, navigate: () -> Unit, onBackPressed: () -> Unit) {

        if (validateData(context)) {
            if (!isForget) {
                checkUserExistence(onBackPressed, navigate)
            }else{
                requestOtp(navigate)
            }

        }

    }

    private fun requestOtp(navigate:()->Unit) {
        viewModelScope.launch {
            val encryptedPhoneNumber =
                Encryption.encryptData("{\"phoneNumber\" :\"+${getSelectedCurrency(appContext)}$phoneNumber\" }")
            repository.sendOtp(encryptedPhoneNumber).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        setToken(appContext, result.data.token)
                        PHONE_NUMBER = "00${getSelectedCurrency(appContext)}" + phoneNumber
                        PHONE_NUMBER_RESEND = phoneNumber
                        navigate()
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

    private fun getConfigs() {
        viewModelScope.launch {
            repository.getConfigs().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isWhatsAppEnabled = result.data.enableWhatsappLogin
                        isSMSEnabled = result.data.enableOtpLogin
                        isTelegramEnabled = true// todo change this when telegram is added
                        isBiometricEnabled = result.data.enableBiometricLogin
                    }

                    is Resource.Loading -> {
                        // nothing now
                    }

                    is Resource.Error -> {
                    }
                }
            }
        }
    }

    private fun validateData(context: Context): Boolean {
        if (phoneNumber.isEmpty()) {
            showMessage(context, "Please Enter Phone Number")
            return false
        }
        if (phoneNumber.length != 9) {
            showMessage(context, "Not A Valid Phone Number")
            return false
        }
        return true
    }

    override fun onCleared() {
        NAVIGATION_PHONE_NUMBER = null
        super.onCleared()
    }
}