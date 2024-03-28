package com.simma.simmaapp.presentation.loginMethodScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.simma.simmaapp.remote.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginMethodViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    var showBiometricPrompt by mutableStateOf(
        false
    )
}