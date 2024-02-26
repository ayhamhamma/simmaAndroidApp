package com.simma.simmaapp.presentation.loginScreen

sealed class Screen(val route: String) {
    object LoginScreen : Screen("LoginScreen")
    object RegistrationScreen : Screen("RegistrationScreen")
    object OtpScreen : Screen("OtpScreen")
    object SetPasswordScreen : Screen("SetPasswordScreen")
}