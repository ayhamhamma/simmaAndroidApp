package com.simma.simmaapp.presentation.loginMethodScreen

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.loginScreen.Screen
import com.simma.simmaapp.presentation.theme.Dimen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.OnBoardingBabyBlue
import com.simma.simmaapp.presentation.theme.SimmaBlue

@Preview
@Composable
fun LoginMethodScreen(
    navController: NavController? = null,
    biometricPromptVisible: Boolean = false,
    biometricPrompt: BiometricPrompt? = null,
    promptInfo: BiometricPrompt.PromptInfo? =null,
    showBiometricPrompt : ((Boolean)->Unit)? = {}
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        ((context) as LoginActivity).window.apply {
            navigationBarColor =
                ContextCompat.getColor(context, R.color.white)
            statusBarColor =
                ContextCompat.getColor(context, R.color.babyBlue1)
        }
    }

    LaunchedEffect(biometricPromptVisible) {
        if(biometricPromptVisible){
            biometricPrompt?.authenticate(promptInfo!!)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar()
        }
    ) { paddingValues ->
        MainSignup(paddingValues, navController,showBiometricPrompt = {
            showBiometricPrompt?.invoke(it)
        })
    }
}

@Preview
@Composable
fun TopAppBar() {
    val painter = painterResource(id = R.drawable.simma_logo1)
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = Dimen.PADDING, end = Dimen.PADDING, top = Dimen.PADDING),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(painter = painter, contentDescription = "Simma", modifier = Modifier.size(60.dp))
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(SimmaBlue), contentAlignment = Alignment.Center
        ) {
            Text(
                text = "العربيه",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.font_med))
                ),
                modifier = Modifier.padding(start = 15.dp, end = 15.dp, top = 10.dp, bottom = 10.dp)

            )

        }

    }
}


@Composable
fun MainSignup(paddingValues: PaddingValues, navController: NavController? = null, showBiometricPrompt: ((Boolean) -> Unit)? = null) {
    val context = LocalContext.current
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .clip(RoundedShape())
                .background(OnBoardingBabyBlue),
        ) {


            Image(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .aspectRatio(1.5f),
                painter = painterResource(id = R.drawable.login_image_2),
                contentDescription = "Login Image",
                contentScale = ContentScale.Crop

            )
            Text(
                text = "Welcome Back! ",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontWeight = FontWeight(1000),
                    color = SimmaBlue,
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = paddingValues.calculateTopPadding() + 20.dp),
            )

        }




        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.biometric_icon),
                contentDescription = "Login via biometric",
                modifier = Modifier.size(100.dp).clickable {
                    showBiometricPrompt?.invoke(true)
                }
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING)
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            navController?.navigate(Screen.LoginWithPasswordScreen.route)
                        }
                        .background(Yellow)
                        .fillMaxWidth()
                        .height(56.dp), contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Log In with Password",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 24.sp,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily(Font(R.font.font)),
                            color = Color(0xFF2D2D2D),
                        )
                    )
                }
                Row {
                    val interactionSource = remember { MutableInteractionSource() }
                    val context = LocalContext.current
                    Text(
                        text = "Don’t have an account? ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily(Font(R.font.font)),
                        color = Color(0xFF4A4A4A),
                    )
                    Text(
                        text = "Sign up",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF4A4A4A),
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            (context as LoginActivity).onBackPressedDispatcher.onBackPressed()
                        }
                    )
                }
            }
        }
    }
}
