package com.simma.simmaapp.presentation.setupPassword

import android.content.Intent
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.PreviewActivity
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
import com.simma.simmaapp.presentation.theme.Dimen.PADDING

@Preview(showBackground = true)
@Composable
fun SetUpPassword(navController: NavController? = null,
                  password : String = "",
                  confirmPassword : String = "",
                  onPasswordChange : (String) ->Unit = {},
                  onConfirmPasswordChange : (String) ->Unit = {},
                  onDoneClick : () ->Unit = {},
                  sixCharState : ValidationState = ValidationState(),
                  oneUpperCaseState : ValidationState = ValidationState(),
                  oneNumberState : ValidationState = ValidationState(),
                  isLoading : Boolean = false
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        ((context) as LoginActivity).window.apply {
            navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
            statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        }
    }
    Scaffold(
        topBar = {
            AppBar(navController)
        },
        modifier = Modifier
            .fillMaxSize(),
        containerColor = Color.White,
        bottomBar = {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Box(
                    Modifier
                        .padding(start = PADDING, end = PADDING, bottom = 30.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            onDoneClick.invoke()
                        }
                        .background(Yellow)
                        .height(56.dp)
                    , contentAlignment = Alignment.Center
                ) {
                    AnimatedVisibility(visible = !isLoading, exit = shrinkHorizontally(), enter = expandHorizontally()) {
                        Text(
                            text = "Done",
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
                    AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
                        LottieAnimation(
                            composition = composition,
                            iterations = LottieConstants.IterateForever,
                            modifier = Modifier.height(30.dp).width(100.dp)
                        )
                    }
                }
            }

        }
    )
    { padding ->


        Column(
            Modifier
                .padding(top = padding.calculateTopPadding(), start = PADDING, end = PADDING)
                .fillMaxWidth()
        ) {
            Text(
                text = "For easier access next time create a password",
                style = TextStyle(
                    fontSize = 23.sp,
                    fontFamily = FontFamily(Font(R.font.font_bold)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(16.dp))
            Text(
                text = "New Password",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onTextChange = {
                        onPasswordChange(it)
                    },
                    text = password
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "Confirm Password",
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(8.dp))
            Box(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    onTextChange = {
                        onConfirmPasswordChange(it)
                    },
                    text = confirmPassword
                )
            }
            Spacer(modifier = Modifier.size(22.dp))
            Text(
                text = "Password must contain",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 22.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
                )
            )
            Spacer(modifier = Modifier.size(11.dp))
            validationItem(
                isValidated = sixCharState.isValidated,
                isEmpty = sixCharState.isEmpty,
                text = "Be at least 6 characters"
            )
            Spacer(modifier = Modifier.size(8.dp))
            validationItem(
                isValidated = oneUpperCaseState.isValidated,
                isEmpty = oneUpperCaseState.isEmpty,
                text = "Include at least 1 uppercase"
            )
            Spacer(modifier = Modifier.size(8.dp))
            validationItem(
                isValidated = oneNumberState.isValidated,
                isEmpty = oneNumberState.isEmpty,
                text = "Include at least 1 number"
            )
            Spacer(modifier = Modifier.size(32.dp))
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AppBar(navController:NavController? = null) {
    val backPainter = painterResource(id = R.drawable.back_btn)
    val helpPainter = painterResource(id = R.drawable.help_icon)
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(start = PADDING, end = PADDING),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Image(
            painter = backPainter,
            contentDescription = "back button",
            modifier = Modifier
                .size(32.dp)
                .padding(5.dp)
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = helpPainter,
                contentDescription = "back button",
                modifier = Modifier
                    .size(32.dp).
                clickable {
                    navController?.navigate(Screen.HelpCenterScreen.route)
                }
            )

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit,
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }
    var showPassword by remember {
        mutableStateOf(false)
    }
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
            visualTransformation = if (showPassword) VisualTransformation.None else PasswordVisualTransformation(),
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
                        style = TextStyle(textAlign = TextAlign.Start)
                    )
                },
                container = {
                    OutlinedTextFieldDefaults.ContainerBox(
                        enabled = true,
                        isError = false,
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
            Box(
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(24.dp)
                    .align(Alignment.CenterEnd)
                    .clip(CircleShape)
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
                    },
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.eye_icon),
                    contentDescription = "delete all phone number ",
                    Modifier
                        .size(24.dp)
                )
            }
        }
    }
}

@Composable
fun validationItem(
    isValidated: Boolean = true,
    text: String = "",
    isEmpty: Boolean = false
) {
    val checkPainter = painterResource(id = R.drawable.validation_success)
    val crossPainter = painterResource(id = R.drawable.validation_failed)
    val colorPainter = painterResource(id = R.drawable.verification_not_verifide)
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(17.dp)
                .clip(CircleShape)
        ) {
            Image(
                painter = if (isValidated) checkPainter else {
                    if (isEmpty) colorPainter else crossPainter
                },
                contentDescription = "",
                Modifier.size(17.dp)
            )
        }
        Spacer(modifier = Modifier.size(8.dp))
        Text(
            text = text,
            style = TextStyle(
                fontSize = 14.sp,
                lineHeight = 22.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(400),
                color = Color(0xFF545454),
                letterSpacing = 0.28.sp,
            )
        )

    }
}