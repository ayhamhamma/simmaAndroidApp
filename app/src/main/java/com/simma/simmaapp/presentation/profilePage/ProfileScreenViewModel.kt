package com.simma.simmaapp.presentation.profilePage

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.simma.simmaapp.model.citiesModel.CitiesModelItem
import com.simma.simmaapp.model.updateProfile.Address
import com.simma.simmaapp.model.updateProfile.UpdateProfileRequestBody
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Helpers
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    val repository: Repository,
    @ApplicationContext val appContext: Context
) : ViewModel() {

    var deliveryCity = ""
    var deliveryCityName by mutableStateOf("City")
    var citiesList by mutableStateOf<List<CitiesModelItem>>(emptyList())
    var isFirstNameError by mutableStateOf(false)
    var isLastNameError by mutableStateOf(false)
    var isCityError by mutableStateOf(false)
    var isAddressError by mutableStateOf(false)
    var nameText by mutableStateOf("")
    var phoneText by mutableStateOf("")
    var genderText by mutableStateOf("")
    var dateOfBirthText by mutableStateOf("")
    var emailText by mutableStateOf("")
    var addressText by mutableStateOf("")
    var lastName by mutableStateOf("")
    var isLoading by mutableStateOf(true)
    var isButtonLoading by mutableStateOf(false)



    init {
        getCities()
    }

    private fun getMyProfile() {
        val token = Helpers.getToken(appContext)
        viewModelScope.launch {
            repository.getMyProfile(token).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        nameText = result.data.firstName
                        phoneText = result.data.phoneNumber
                        emailText = result.data.email
                        addressText = result.data.address?.addressDetails ?: ""
                        if (citiesList.isNotEmpty() &&result.data.address != null) {
                            deliveryCityName = citiesList.first {
                                it.id == result.data.address.cityId
                            }.name.en
                            deliveryCity = citiesList.first {
                                it.id == result.data.address.cityId
                            }.id
                        }
                        lastName = result.data.lastName
                    }

                    is Resource.Error -> {
                    }

                    is Resource.Loading -> {
                        isLoading = result.isLoading
                    }
                    else->{

                    }
                }
            }
        }
    }

    private fun getCities() {
        viewModelScope.launch {
            repository.getCities().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        citiesList = result.data
                        getMyProfile()
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        Unit
                    }
                    else ->{

                    }
                }
            }
        }
    }

    fun onSaveClick(navController: NavController) {
        isFirstNameError = false
        isAddressError = false
        isCityError = false
        if (nameText.isEmpty() || nameText.isBlank()) {
            isFirstNameError = true
            return
        }
        if (lastName.isEmpty() || lastName.isBlank()) {
            isLastNameError = true
            return
        }
        if (deliveryCity.isEmpty() || deliveryCity.isBlank()) {
            isCityError = true
            return
        }
        if (addressText.isEmpty() || addressText.isBlank()) {
            isAddressError = true
            return
        }

        callAPI(navController)
    }

    private fun callAPI(navController: NavController) {
        val data = UpdateProfileRequestBody(
            firstName = nameText,
            lastName = lastName,
            language = "ar",// todo change this
            email = if (emailText.isEmpty() || emailText.isBlank()) null else emailText,
            address = Address(
                cityId = deliveryCity,
                addressDetails = addressText
            )
        )
        val token = Helpers.getToken(appContext)
        viewModelScope.launch {
            repository.updateProfile(data, token).collect { result ->
                when (result) {
                    is Resource.Success -> {
                        // Navigate to HomeScreen and pop all screens from the back stack
                        navController.navigate(HomeScreens.HomeScreen.route){
                            popUpTo(HomeScreens.HomeScreen.route){
                                inclusive = true
                            }
                        }
                        showMessage(appContext, "Profile updated successfully")
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        isButtonLoading = result.isLoading
                    }
                    else->{

                    }
                }
            }
        }
    }
}