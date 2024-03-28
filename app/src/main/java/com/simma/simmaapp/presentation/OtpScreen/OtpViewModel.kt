package com.simma.simmaapp.presentation.OtpScreen

import android.content.Context
import android.os.Build
import android.util.Log.e
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.presentation.theme.ActiveResendCodeColor
import com.simma.simmaapp.presentation.theme.InActiveResendCodeColor
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants
import com.simma.simmaapp.utils.Constants.PHONE_NUMBER
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers
import com.simma.simmaapp.utils.Helpers.getSelectedCurrency
import com.simma.simmaapp.utils.Helpers.getToken
import com.simma.simmaapp.utils.Helpers.setToken
import com.simma.simmaapp.utils.Helpers.setUserId
import com.simma.simmaapp.utils.Helpers.setUserPhoneNumber
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OtpViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {

    var termsAndConditionsState by mutableStateOf(false)
    var otpText = mutableStateListOf("", "", "", "", "", "")
    private val _timerState = MutableStateFlow("45")
    val timerState: StateFlow<String> = _timerState
    var resendColor by mutableStateOf(InActiveResendCodeColor)
    private var timerJob: Job? = null
     var resendIsActive by mutableStateOf(false)

    init {
        startTimer()
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {

            for (i in 45 downTo 0) {
                if (i >= 10) {
                    _timerState.value = i.toString()
                    delay(1000)
                } else {
                    _timerState.value = "0${i}"
                    delay(1000)
                }
            }
            resendColor = ActiveResendCodeColor
            resendIsActive = true


        }
    }

    // Update the OTP text at a specific index
    fun updateOtpAtIndex(index: Int, value: String) {
        otpText[index] = value
    }



    fun resendOTP(context:Context) {
        resendColor = InActiveResendCodeColor
        resendIsActive = false
        // Cancel the existing timer job
        timerJob?.cancel()
        // Reset OTP text
        otpText = mutableStateListOf("", "", "", "", "", "")
        // Restart the timer
        startTimer()

        // Call API
        val encryptedPhoneNumber = Encryption.encryptData("{\"phoneNumber\" :\"+${getSelectedCurrency(appContext)}${Constants.PHONE_NUMBER_RESEND}\" }")
        viewModelScope.launch {
            repository.sendOtp(encryptedPhoneNumber).collect{
                    result ->
                when(result){
                    is Resource.Success->{
                        Helpers.setToken(context, result.data.token)

                    }
                    is Resource.Loading->{
                        // nothing now
                    }
                    is Resource.Error->{
                        Helpers.showMessage(context, "Error Occurred")
                    }
                }
            }
        }
    }

    fun verifyOtp(context: Context,navigate: () -> Unit) {
        if (verifyOtpText()) {
            viewModelScope.launch {
                val otpTextString = otpText.joinToString(separator = "")
                e("ayham",otpTextString)
                val encryptedData = Encryption.encryptData("{\"otp\":\"$otpTextString\"}")
                val token = getToken(context)
                repository.verifyOtp(encryptedData, token).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            setToken(context, result.data.token)
                            getUserData(navigate)


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
        }else{
            showMessage(context,"Invalid Code Retry...")
        }
    }

    private fun verifyOtpText(): Boolean {
        otpText.forEach {
            if(it.isEmpty()||it.isBlank()){
                return false
            }
        }
        return true
    }
    private fun getUserData(navigate: () -> Unit) {
        viewModelScope.launch {

            val data = "{\"firstName\":\"\",\"lastName\":\"\",\"email\":\"\",\"otpToken\":true}"
            val encryptedData = Encryption.encryptData(data)
            val token = getToken(context =appContext)

            repository.getUserData(encryptedData, token).collect {
                    result ->
                when (result) {
                    is Resource.Success -> {
                        setToken(appContext,result.data.token.accessToken)
                        setUserId(appContext,result.data.user.id)
                        setUserPhoneNumber(appContext,result.data.user.phoneNumber)
                        e("ayhamUserId",result.data.user.id)
                        navigate()
                    }
                    is Resource.Error -> {
                        Unit
                    }
                    is Resource.Loading -> {
                        // not implemented
                    }
                }
            }
        }
    }
}

