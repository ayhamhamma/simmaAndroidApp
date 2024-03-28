package com.simma.simmaapp.presentation.loginScreen

import com.simma.simmaapp.presentation.homeScreen.HomeScreens

sealed class Screen(val route: String) {
    object LoginScreen : Screen("LoginScreen")
    object RegistrationScreen : Screen("RegistrationScreen")
    object OtpScreen : Screen("OtpScreen")
    object SetPasswordScreen : Screen("SetPasswordScreen")
    object OnBoardingScreen : Screen("OnBoardingScreen")
    object LoginMethodScreen : Screen("LoginMethodScreen")
    object LoginWithPasswordScreen : Screen("LoginWithPasswordScreen")
    object HelpCenterScreen : Screen("HelpCenterScreen")
    object SendInquiryScreen : Screen("SendInquiryScreen")
    object ContactUsScreen : Screen("ContactUsScreen")
    object FrequentlyAskedQuestions : Screen("FrequentlyAskedQuestions")
    object TermsAndConditionsScreen : Screen("TermsAndConditionsScreen")
}