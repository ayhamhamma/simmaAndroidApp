package com.simma.simmaapp.presentation.registrationView

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants.PHONE_NUMBER
import com.simma.simmaapp.utils.Helpers.setToken
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.simma.simmaapp.utils.Encryption
import com.simma.simmaapp.utils.Helpers.setUserId

@HiltViewModel
class RegistrationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    var referralText by mutableStateOf("")
    var phoneNumber by mutableStateOf("")
    var textMessageSelected by mutableStateOf(false)
    var whatsAppSelected by mutableStateOf(false)


    fun onButtonClick(context: Context, navigate: () -> Unit) {
        if (validateData(context)) {
            val encryptedPhoneNumber = Encryption.encryptData("{\"phoneNumber\" :\"+962$phoneNumber\" }")
            viewModelScope.launch {
                repository.sendOtp(encryptedPhoneNumber).collect{
                        result ->
                    when(result){
                        is Resource.Success->{
                            setToken(context,result.data.token)
                            PHONE_NUMBER = "00962"+phoneNumber
                            navigate()
                        }
                        is Resource.Loading->{
                            // nothing now
                        }
                        is Resource.Error->{
                            showMessage(context,"Error Occurred")
                        }
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
        if(phoneNumber.length !=9){
            showMessage(context,"Not A Valid Phone Number")
            return false
        }
        return true
    }
}