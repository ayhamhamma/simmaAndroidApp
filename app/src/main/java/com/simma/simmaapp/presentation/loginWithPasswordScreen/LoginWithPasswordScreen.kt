package com.simma.simmaapp.presentation.loginMethodScreen

import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.loginScreen.Screen
import com.simma.simmaapp.presentation.theme.BorderColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.OnBoardingBabyBlue
import com.simma.simmaapp.presentation.theme.SimmaBlue
import com.simma.simmaapp.utils.Helpers

@Preview
@Composable
fun LoginWithPasswordScreen(
    navController: NavController? = null,
    signIn: (() -> Unit) = {},
    phoneNumberText: String = "",
    passwordText: String = "",
    changePhoneNumberText: (String) -> Unit = {},
    changePasswordText: (String) -> Unit = {},
    isPasswordVisible: Boolean = false,
    onLoginPress: () -> Unit = {},
    phoneNumberError: Boolean = false,
    passwordError: Boolean = false,
    isLoading: Boolean = false,
    clickableButton: Boolean = true

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

    Scaffold(
        topBar = {
            AppBar()
        },
        containerColor = Color.White
    ) { paddingValues ->
        MainScreen(
            paddingValues,
            navController,
            phoneNumberText,
            passwordText,
            changePhoneNumberText,
            changePasswordText,
            isPasswordVisible,
            onLoginPress,
            phoneNumberError,
            passwordError,
            isLoading,
            clickableButton
        )
    }
}

@Preview
@Composable
fun AppBar() {
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    paddingValues: PaddingValues,
    navController: NavController? = null,
    phoneNumberText: String,
    passwordText: String,
    changePhoneNumberText: (String) -> Unit,
    changePasswordText: (String) -> Unit,
    isPasswordVisible: Boolean,
    onLoginPress: () -> Unit,
    phoneNumberError: Boolean,
    passwordError: Boolean,
    isLoading: Boolean,
    clickableButton: Boolean
) {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var selectedCurrency by remember {
        mutableStateOf("+${Helpers.getSelectedCurrency(context)}")
    }
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
            verticalArrangement = if (isPasswordVisible) Arrangement.Center else Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING)
            ) {
                Row {
                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .height(48.dp)
                            .weight(1f)

                            .border(
                                1.dp, BorderColor,
                                RoundedCornerShape(12.dp)
                            ),
                        expanded = isExpanded,
                        onExpandedChange = {
                            isExpanded = it
                        }
                    ) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text(
                                text = selectedCurrency,
                                fontSize = 20.sp,
                                fontFamily = FontFamily(Font(R.font.font)),
                                fontWeight = FontWeight(400),
                                textAlign = TextAlign.Center,
                                color = MedDarkGrey,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor()
                            )
                        }
                        ExposedDropdownMenu(expanded = isExpanded, onDismissRequest = {
                            isExpanded = false
                        }) {
                            DropdownMenuItem(text = { Text(text = "+962") }, onClick = {
                                selectedCurrency = "+962"
                                Helpers.setSelectedCurrency(context, "962")
                            })
                            DropdownMenuItem(text = { Text(text = "+964") }, onClick = {
                                selectedCurrency = "+964"
                                Helpers.setSelectedCurrency(context, "964")
                            })
                        }
                    }

                    Spacer(modifier = Modifier.size(12.dp))
                    OutlinedTextField(
                        modifier = Modifier
                            .height(48.dp)
                            .weight(3f), onTextChange = {
                            changePhoneNumberText(it)
                        },
                        hint = "Phone Number",
                        text = phoneNumberText,
                        isError = phoneNumberError,
                        isNumbers = true
                    )
                }
                Spacer(modifier = Modifier.size(15.dp))
                AnimatedVisibility(isPasswordVisible) {
                    Column(Modifier.fillMaxWidth()) {


                        OutlinedTextField(
                            modifier = Modifier
                                .height(48.dp), onTextChange = {
                                changePasswordText(it)
                            },
                            hint = "Password",
                            text = passwordText,
                            isError = passwordError,
                            isPassword = true
                        )
                        Spacer(modifier = Modifier.size(8.dp))
                        Box(
                            Modifier.fillMaxWidth(),

                            ) {
                            Text(
                                text = "Forget password?",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.font)),
                                    fontWeight = FontWeight(400),
                                    color = MedDarkGrey,
                                ),
                                modifier = Modifier
                                    .clickable {
                                        navController?.navigate(Screen.RegistrationScreen.route + "?isForget=true")
                                    }
                                    .align(Alignment.TopEnd)
                            )
                        }
                        Spacer(modifier = Modifier.size(30.dp))

                    }
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = PADDING, end = PADDING)
            ) {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))
                Row(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onLoginPress()
                        }
                        .background(if (clickableButton) Yellow else CheckoutAppBarColor)
                        .height(56.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AnimatedVisibility(
                        visible = !isLoading,
                        exit = shrinkHorizontally(),
                        enter = expandHorizontally()
                    ) {
                        Text(
                            text = "Login",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 24.sp,
                                fontFamily = FontFamily(Font(R.font.font)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFF2D2D2D),
                                textAlign = TextAlign.Center
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    AnimatedVisibility(
                        visible = isLoading,
                        enter = fadeIn(),
                        exit = fadeOut()
                    ) {
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier
                                .height(30.dp)
                                .width(100.dp)
                        )
                    }
                }
                Row {
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        text = "Don’t have an account? ",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        fontFamily = FontFamily(Font(R.font.font)),
                        color = MedDarkGrey,
                    )
                    Text(
                        text = "Sign up",
                        fontSize = 14.sp,
                        fontWeight = FontWeight(1000),
                        color = MedDarkGrey,
                        fontFamily = FontFamily(Font(R.font.font_bold)),
                        textDecoration = TextDecoration.Underline,
                        modifier = Modifier.clickable(
                            interactionSource = interactionSource,
                            indication = null
                        ) {
                            navController?.popBackStack()
                            navController?.popBackStack()
                            navController?.navigate(Screen.RegistrationScreen.route)

                        }
                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit,
    text: String,
    isError: Boolean,
    isNumbers: Boolean = false,
    isPassword: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showPassword by remember {
        mutableStateOf(false)
    }
    val visualTransformation =
        if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    Box(
        modifier
            .fillMaxWidth()
            .height(40.dp)
    ) {


        BasicTextField(
            value = text,
            singleLine = true,
            interactionSource = interactionSource,
            cursorBrush = SolidColor(Color.White),
            onValueChange = { newText ->
                onTextChange(newText)
            },
            visualTransformation = if (showPassword) VisualTransformation.None else visualTransformation,
            keyboardOptions = if (isNumbers) KeyboardOptions(keyboardType = KeyboardType.Phone) else KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White),
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = true,
                contentPadding = PaddingValues(start = 10.dp, end = 10.dp),
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    Text(
                        text = hint,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            color = BorderColor,
                            fontSize = 18.sp
                        )
                    )
                },
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = true,
                        isError = isError,
                        interactionSource = interactionSource,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Yellow, // You can adjust the color as needed
                            unfocusedBorderColor = BorderColor
                        ),
                        shape = RoundedCornerShape(10.dp),
                        focusedBorderThickness = 1.dp,
                        unfocusedBorderThickness = 1.dp,
                    )
                }
            )
        }
        if (text.isNotEmpty()) {
            Box(modifier = Modifier
                .padding(end = 10.dp)
                .size(18.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .clickable {
                    onTextChange("")
                }
                .background(if (isPassword) Color.White else BorderColor),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = if (isPassword) painterResource(id = R.drawable.eye_icon) else painterResource(
                        id = R.drawable.delete_text
                    ),
                    contentDescription = "delete all phone number ",
                    modifier = if (isPassword) {
                        Modifier
                            .padding(0.dp)
                            .size(16.dp)
                            .pointerInteropFilter { event ->
                                when {
                                    event.action == MotionEvent.ACTION_DOWN -> {
                                        showPassword = true
                                        true
                                    }

                                    event.action == MotionEvent.ACTION_UP -> {
                                        showPassword = false
                                        true
                                    }

                                    else -> false
                                }
                            }
                    } else {
                        Modifier
                            .padding(4.dp)
                            .size(16.dp)
                    }
                )
            }
        }
    }
}