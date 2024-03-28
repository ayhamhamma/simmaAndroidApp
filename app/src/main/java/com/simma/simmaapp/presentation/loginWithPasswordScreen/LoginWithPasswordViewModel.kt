package com.simma.simmaapp.presentation.loginWithPasswordScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants.NAVIGATION_PHONE_NUMBER
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers
import com.simma.simmaapp.utils.Helpers.getSelectedCurrency
import com.vs.simma.model.auth.SendOtpRequestBody
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import java.net.URLEncoder
import javax.inject.Inject

@HiltViewModel
class LoginWithPasswordViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext : Context
) : ViewModel() {
    var phoneNumber by mutableStateOf(
        ""
    )
    var password by mutableStateOf(
        ""
    )
    var isPasswordVisible by mutableStateOf(
        false
    )
    var phoneNumberError by mutableStateOf(
        false
    )
    var passwordError by mutableStateOf(
        false
    )
    var isLoading by mutableStateOf(false)

    var clickableButton by mutableStateOf(false)

    fun checkUser() {
        viewModelScope.launch {
            repository.checkUserExistence("+${getSelectedCurrency(appContext)}$phoneNumber").collect { result ->
                when (result) {
                    is Resource.Success -> {
                        isPasswordVisible = result.data.profileCompleted
                    }

                    is Resource.Loading -> Unit

                    is Resource.Error -> {
                        passwordError = true
                    }
                }
            }
        }

    }

    fun validateData(navigateToSetPassword : () -> Unit, startHome:()->Unit) {
        if (phoneNumber.isEmpty()) {
            phoneNumberError = true
            LoginActivity.showToast(false,"Invalid phone number")
            return
        }
        if (phoneNumber.length < 9) {
            phoneNumberError = true
            LoginActivity.showToast(false,"Invalid phone number")
            return
        }
        if (isPasswordVisible){
            if (password.isEmpty()) {
                passwordError = true
                LoginActivity.showToast(false,"Please enter password")
                return
            }
            if (password.length < 6) {
                passwordError = true
                LoginActivity.showToast(false,"Invalid Password")
                return
            }
            login(startHome)
        }else{
            NAVIGATION_PHONE_NUMBER = phoneNumber
            navigateToSetPassword.invoke()
        }
    }

    fun login(startHome: () -> Unit) {
        viewModelScope.launch {
            val encryptedData = Encryption.encryptData(
                "{\n" +
                        "    \"password\": \"$password\",\n" +
                        "    \"phoneNumber\": \"+${getSelectedCurrency(appContext)}$phoneNumber\"\n" +
                        "}"
            )
            val data = SendOtpRequestBody(
                encryptedData
            )
            repository.login(data).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        Helpers.setToken(appContext, result.data.token.accessToken)
                        Helpers.setUserId(appContext, result.data.user.id)
                        Helpers.setUserPhoneNumber(appContext, result.data.user.phoneNumber)
                        startHome()
                    }

                    is Resource.Loading -> {
                        isLoading = result.isLoading
                    }

                    is Resource.Error -> {
                        LoginActivity.showToast(false,"Phone number or password is incorrect")
                    }
                }
            }
        }
    }

}