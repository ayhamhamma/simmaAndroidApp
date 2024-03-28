package com.simma.simmaapp.presentation.loginScreen

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.OtpScreen.OtpScreen
import com.simma.simmaapp.presentation.frequentlyAskedQuestionsScreen.FrequentlyAskedQuestionsScreen
import com.simma.simmaapp.presentation.helpCenterScreen.HelpCenterScreen
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.loginMethodScreen.LoginMethodScreen
import com.simma.simmaapp.presentation.loginMethodScreen.LoginMethodViewModel
import com.simma.simmaapp.presentation.loginMethodScreen.LoginWithPasswordScreen
import com.simma.simmaapp.presentation.loginWithPasswordScreen.LoginWithPasswordViewModel
import com.simma.simmaapp.presentation.onBoardingScreen.OnBoardingScreen
import com.simma.simmaapp.presentation.registrationView.RegistrationScreen
import com.simma.simmaapp.presentation.sendInquiryScreen.SendInquiryScreen
import com.simma.simmaapp.presentation.setupPassword.SetUpPassword
import com.simma.simmaapp.presentation.setupPassword.SetUpPasswordViewModel
import com.simma.simmaapp.presentation.theme.Dimen
import com.simma.simmaapp.presentation.theme.ErrorToastBackgroundColor
import com.simma.simmaapp.presentation.webview.TermsAndConditionsScreen
import com.simma.simmaapp.utils.Helpers.getNumberOfTimes
import com.simma.simmaapp.utils.Helpers.incrementNumberOfTimes
import com.simma.simmaapp.utils.Helpers.isFirstTimeOpen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginActivity : FragmentActivity() {
    companion object {

        var showErrorToast = MutableStateFlow(false)
        var showSuccessToast = MutableStateFlow(false)
        var toastMessageText = MutableStateFlow("")

        /**
         * function to show toast for all Login Screens
         * @param type true -> Success false -> Error
         * @param text the toast message
         */
        fun showToast(
            type: Boolean,
            text: String,
        ) {
            if (type) {

                GlobalScope.launch {
                    toastMessageText.update {
                        text
                    }
                    showSuccessToast.update {
                        true
                    }
                    delay(2000)

                    showSuccessToast.update {
                        false
                    }
                }
            } else {
                GlobalScope.launch {

                    toastMessageText.update {
                        text
                    }
                    showErrorToast.update {
                        true
                    }

                    delay(2000)
                    showErrorToast.update {
                        false
                    }
                }
            }

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1000212) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, handle it here
                // For example, update UI or perform action
            } else {
                incrementNumberOfTimes(this)
            }
        }
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (getNumberOfTimes(this) < 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        android.Manifest.permission.POST_NOTIFICATIONS
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(android.Manifest.permission.POST_NOTIFICATIONS),
                        1000212
                    )
                } else {
                    // Permission already granted
                }
            } else {
                // For versions below Android 13, notification permission is not required
            }
        } else {
            incrementNumberOfTimes(this)
        }
        setContent {
            Box(modifier = Modifier.fillMaxSize()) {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = if (isFirstTimeOpen(this@LoginActivity)) Screen.OnBoardingScreen.route else Screen.LoginScreen.route
                ) {
                    composable(route = Screen.LoginScreen.route) {
                        LoginSignupScreen(navController)
                    }
                    composable(route = Screen.OnBoardingScreen.route) {

                        OnBoardingScreen(navController)
                    }
                    composable(
                        route = Screen.RegistrationScreen.route + "?isForget={forget}",
                        arguments = listOf(navArgument("forget") {
                            type = NavType.BoolType
                            defaultValue = false
                            nullable = false
                        })
                    ) { backStackEntry ->
                        val isForgetPass = backStackEntry.arguments?.getBoolean("forget") ?: false
                        RegistrationScreen(navController, isForget = isForgetPass)
                    }
                    composable(
                        route = Screen.OtpScreen.route + "?isForget={forget}",
                        arguments = listOf(navArgument("forget") {
                            type = NavType.BoolType
                            defaultValue = false
                            nullable = false
                        })
                    ) { backStackEntry ->
                        val isForgetPass = backStackEntry.arguments?.getBoolean("forget") ?: false
                        OtpScreen(navController, isForgetPass)
                    }
                    composable(route = Screen.SetPasswordScreen.route) {
                        val viewModel: SetUpPasswordViewModel = hiltViewModel()
                        SetUpPassword(
                            navController,
                            password = viewModel.password,
                            confirmPassword = viewModel.confirmPassword,
                            onPasswordChange = {
                                viewModel.password = it
                                viewModel.validatePasswordAsInput()
                            },
                            onConfirmPasswordChange = {
                                viewModel.confirmPassword = it
                            },
                            onDoneClick = {
                                viewModel.onDoneClick(navController,
                                    navigate = {
                                        startActivity(
                                            Intent(
                                                this@LoginActivity,
                                                HomeActivity::class.java
                                            ).apply {

                                            })
                                    },
                                    showToast = { message, type ->
                                        showToast(type, message)
                                    },
                                    finishActivity = {
                                        finish()
                                    }
                                )
                            },
                            sixCharState = viewModel.sixCharValidation,
                            oneUpperCaseState = viewModel.oneUpperCaseValidation,
                            oneNumberState = viewModel.oneNumberValidation,
                            isLoading = viewModel.isLoading
                        )
                    }
                    composable(route = Screen.LoginMethodScreen.route) {
                        val viewModel: LoginMethodViewModel = hiltViewModel()

                        val executor = ContextCompat.getMainExecutor(this@LoginActivity)

                        var promptInfo = BiometricPrompt.PromptInfo.Builder()
                            .setTitle("title")
                            .setSubtitle("subtitle")
                            .setAllowedAuthenticators(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                            .build()

                        val biometricPrompt = BiometricPrompt(this@LoginActivity, executor,
                            object : BiometricPrompt.AuthenticationCallback() {
                                override fun onAuthenticationError(
                                    errorCode: Int,
                                    errString: CharSequence
                                ) {
                                    super.onAuthenticationError(errorCode, errString)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "error",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }

                                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                    super.onAuthenticationSucceeded(result)
                                    Toast.makeText(
                                        this@LoginActivity,
                                        "succeeded!",
                                        Toast.LENGTH_LONG
                                    )
                                        .show()
                                    startActivity(
                                        Intent(
                                            this@LoginActivity,
                                            HomeActivity::class.java
                                        )
                                    )
                                    finish()
                                }

                                override fun onAuthenticationFailed() {
                                    super.onAuthenticationFailed()
                                    Toast.makeText(
                                        this@LoginActivity, "failed", Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        )
                        LoginMethodScreen(
                            navController,
                            viewModel.showBiometricPrompt,
                            biometricPrompt,
                            promptInfo,
                            {
                                viewModel.showBiometricPrompt = it
                            }
                        )
                    }
                    composable(route = Screen.LoginWithPasswordScreen.route) {
                        val viewModel: LoginWithPasswordViewModel = hiltViewModel()
                        LoginWithPasswordScreen(
                            navController,
                            signIn = {},
                            phoneNumberText = viewModel.phoneNumber,
                            passwordText = viewModel.password,
                            changePasswordText = {
                                viewModel.passwordError = false
                                viewModel.password = it
                            },
                            changePhoneNumberText = {
                                viewModel.phoneNumberError = false
                                if (it.length <= 10) {
                                    viewModel.phoneNumber = it
                                    if (it.length >= 9 ) {
                                        viewModel.clickableButton = true
                                        viewModel.checkUser()
                                    }
                                    if (it.length < 9) {
                                        viewModel.clickableButton = false
                                        viewModel.isPasswordVisible = false
                                    }
                                }
                            },
                            isPasswordVisible = viewModel.isPasswordVisible,
                            onLoginPress = {
                                viewModel.validateData({ navController.navigate(Screen.RegistrationScreen.route) },
                                    {
                                        startActivity(
                                            Intent(this@LoginActivity, HomeActivity::class.java)
                                        )
                                    })
                            },
                            phoneNumberError = viewModel.phoneNumberError,
                            passwordError = viewModel.passwordError,
                            isLoading = viewModel.isLoading,
                            clickableButton = viewModel.clickableButton
                        )
                    }
                    composable(route = Screen.HelpCenterScreen.route) {
                        HelpCenterScreen(navController)
                    }
                    composable(route = Screen.SendInquiryScreen.route) {
                        SendInquiryScreen(navController = navController)
                    }
                    composable(route = Screen.ContactUsScreen.route) {
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(Color.Blue)
                        ) {

                        }
                    }
                    composable(route = Screen.FrequentlyAskedQuestions.route) {
                        FrequentlyAskedQuestionsScreen()
                    }
                    composable(route = Screen.TermsAndConditionsScreen.route) {
                        TermsAndConditionsScreen()
                    }

                }
                AnimatedVisibility(
                    visible = (showSuccessToast.collectAsState().value || showErrorToast.collectAsState().value),
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        SuccessToast(
                            text = toastMessageText.collectAsState().value,
                            show = showSuccessToast.collectAsState().value,
                            modifier = Modifier.align(Alignment.Center)
                        )
                        ErrorToast(
                            text = toastMessageText.collectAsState().value,
                            show = showErrorToast.collectAsState().value,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }

        }


    }

    @Preview(showBackground = true)
    @Composable
    fun SuccessToast(
        text: String = "",
        show: Boolean = false,
        modifier: Modifier = Modifier
    ) {
        val painter = painterResource(id = R.drawable.success_icon_toast)
        AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    modifier
                        .padding(Dimen.PADDING)
                        .clip(RoundedCornerShape(24.dp))
                        .background(ErrorToastBackgroundColor)
                        .padding(
                            Dimen.PADDING
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "error",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    fun ErrorToast(
        text: String = "",
        show: Boolean = false,
        modifier: Modifier = Modifier
    ) {
        val painter = painterResource(id = R.drawable.error_toast_icon)
        AnimatedVisibility(visible = show, enter = fadeIn(), exit = fadeOut()) {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Row(
                    modifier
                        .padding(Dimen.PADDING)
                        .clip(RoundedCornerShape(24.dp))
                        .background(ErrorToastBackgroundColor)
                        .padding(
                            Dimen.PADDING
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painter,
                        contentDescription = "error",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(
                        text = text,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontFamily = FontFamily(Font(R.font.font_med)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFFFFFFFF),
                        )
                    )
                }
            }
        }
    }

    sealed class BottomNavItem(val route: String, val label: String, @DrawableRes val icon: Int) {
        object Home : BottomNavItem("home", "home", R.drawable.merchants)
        object Search : BottomNavItem("wallet", "wallet", R.drawable.wallet_icon)
        object Profile : BottomNavItem("my_account", "my_account", R.drawable.account_icon)
    }
}

