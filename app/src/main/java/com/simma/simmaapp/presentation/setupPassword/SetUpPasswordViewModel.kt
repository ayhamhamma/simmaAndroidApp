package com.simma.simmaapp.presentation.setupPassword

import android.content.Context
import android.util.Log.e
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.simma.simmaapp.model.setPasswordRequestBody.SetPasswordRequestBody
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Helpers.getToken
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SetUpPasswordViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")
    var sixCharValidation by mutableStateOf(ValidationState())
    var oneUpperCaseValidation by mutableStateOf(ValidationState())
    var oneNumberValidation by mutableStateOf(ValidationState())
    var isContinueClicked by mutableStateOf(false)
    var isLoading by mutableStateOf(false)
    var timer = 300

    init {
        viewModelScope.launch {
            while (timer > 0) {
                delay(1000)
                timer--
                e("ayham", timer.toString())
            }
        }

    }


    fun validatePasswordAsInput(): Boolean {

        sixCharValidation = if (password.length >= 6) {
            sixCharValidation.copy(
                isValidated = true,
                isEmpty = !isContinueClicked
            )
        } else {
            sixCharValidation.copy(
                isValidated = false,
                isEmpty = !isContinueClicked
            )
        }
        oneUpperCaseValidation = if (hasUpperCase(password)) {
            oneUpperCaseValidation.copy(
                isValidated = true,
                isEmpty = !isContinueClicked
            )
        } else {
            oneUpperCaseValidation.copy(
                isValidated = false,
                isEmpty = !isContinueClicked
            )
        }
        oneNumberValidation = if (containsNumber(password)) {
            oneNumberValidation.copy(
                isValidated = true,
                isEmpty = !isContinueClicked
            )
        } else {
            oneNumberValidation.copy(
                isValidated = false,
                isEmpty = !isContinueClicked
            )
        }
        return (sixCharValidation.isValidated && oneUpperCaseValidation.isValidated && oneNumberValidation.isValidated)

    }

    fun onDoneClick(
        navController: NavController,
        navigate: () -> Unit,
        showToast: (message: String, type: Boolean) -> Unit,
        finishActivity: () -> Unit
    ) {
        if (!validatePassword(showToast)) {
            return
        }
        setPassword(navigate, showToast, navController, finishActivity)
    }

    private fun setPassword(
        navigate: () -> Unit,
        showToast: (message: String, type: Boolean) -> Unit,
        navController: NavController,
        finishActivity  :()->Unit
    ) {
        if (timer > 0) {
            val token = getToken(appContext)
            val body = SetPasswordRequestBody(password = password)
            viewModelScope.launch {
                repository.setPassword(password = body, token).collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            if (result.data.result.success) {
                                navigate.invoke()
                            } else {
                                showToast("Unexpected Error", false)
                            }
                        }

                        is Resource.Loading -> {
                            isLoading = result.isLoading
                        }

                        is Resource.Error -> {
                            navController.popBackStack()
                            navController.popBackStack()
                            navController.popBackStack()
                            showToast("Password set Timeout Please signin again", false)
                        }
                    }
                }
            }
        } else {
            navController.popBackStack()
            navController.popBackStack()
            navController.popBackStack()
            showToast("Password set Timeout Please signin again", false)
        }
    }

    private fun validatePassword(showToast: (message: String, type: Boolean) -> Unit): Boolean {
        isContinueClicked = true
        if (!sixCharValidation.isValidated) {
            showToast("Password must contain at least 6 characters", false)
            return false
        }
        if (!oneUpperCaseValidation.isValidated) {
            showToast("Password must include at least 1 uppercase", false)
            return false
        }
        if (!oneNumberValidation.isValidated) {
            showToast("Password must include at least 1 number", false)
            return false
        }
        if (password != confirmPassword) {
            showToast("Password and confirm password is not the same", false)
            return false
        }
        return true
    }

    private fun hasUpperCase(password: String): Boolean {
        for (char in password) {
            if (char.isUpperCase()) {
                return true
            }
        }
        return false
    }

    private fun containsNumber(password: String): Boolean {
        for (char in password) {
            if (char.isDigit()) {
                return true
            }
        }
        return false
    }


}

data class ValidationState(
    val isValidated: Boolean = false,
    val isEmpty: Boolean = true
)