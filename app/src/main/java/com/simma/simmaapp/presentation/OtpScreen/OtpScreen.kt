package com.simma.simmaapp.presentation.OtpScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusProperties
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.loginScreen.Screen
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.InActiveResendCodeColor
import com.simma.simmaapp.presentation.theme.MedDarkGrey

@Preview(showBackground = true)
@Composable
fun OtpScreen(navController: NavController? = null) {
    val interactionSource = remember { MutableInteractionSource() }
    ((LocalContext.current) as LoginActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val viewModel : OtpViewModel = hiltViewModel()
    val context = LocalContext.current
    val clockPainter = painterResource(id = R.drawable.clock_image)
    Column(modifier = Modifier
        .fillMaxSize()
        .fillMaxSize()
        .background(Color.White)
        .padding(bottom = 30.dp), verticalArrangement = Arrangement.SpaceBetween) {
        Column(
            Modifier
                .padding(start = 30.dp, end = 30.dp)
        ) {
            AppBar()
            Spacer(modifier = Modifier.size(30.dp))
            Text(
                text = "Enter Your verification code",
                fontSize = 23.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "we’ve sent you a 6-digit code to +7899930202",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(400),
                color = MedDarkGrey,
                )
            Spacer(modifier = Modifier.size(34.dp))
            OTPView(navController = navController, viewModel = viewModel )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Resend code in",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(400),
                    color = MedDarkGrey,

                    )
                Spacer(modifier = Modifier.size(6.dp))
                Row(
                    Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(CheckoutAppBarColor)
                        .padding(4.dp), verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = clockPainter,
                        contentDescription = "time",
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.size(9.dp))
                    Text(
                        text = "00:${viewModel.timerState.collectAsState().value}",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(400),
                        color = MedDarkGrey,
                    )
                }
            }
            Text(
                text = "RESEND CODE ",
                fontSize = 18.sp,
                fontWeight = FontWeight(860),
                color = InActiveResendCodeColor,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(R.font.font)),
                textDecoration = TextDecoration.Underline,
                modifier = Modifier.clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    viewModel.resendOTP(context)
                }
            )
        }
    }

}

@Composable
fun OtpChar(
    modifier: Modifier = Modifier,
    onTextChange : (data:String) -> Unit ={},
    keyboardAction : KeyboardActions? = null,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val painter = painterResource(id = R.drawable.dot)
    val pattern = remember { Regex("^[^\\t]*\$") } //to not accept the tab key as value
    var (text, setText) = remember { mutableStateOf("") }
    var isFull by remember {
        mutableStateOf(false)
    }
    val maxChar = 1
    val focusManager = LocalFocusManager.current

    LaunchedEffect(
        key1 = text,
    ) {
        if (text.isNotEmpty()) {
            focusManager.moveFocus(
                focusDirection = FocusDirection.Next,
            )
        }
    }

    Box(
        contentAlignment = Alignment.Center
    ) {
        TextField(
            value = text,
            onValueChange = {
                if (it.length <= maxChar &&
                    ((it.isEmpty() || it.matches(pattern)))
                ) {
                    setText(it)
                    onTextChange(it)
                    isFull = it.isNotEmpty()
                }
            },
            keyboardActions= keyboardAction ?:KeyboardActions.Default
            ,
            modifier = modifier
                .width(50.dp)
                .onKeyEvent {
                    if (it.key == Key.Tab) {
                        focusManager.moveFocus(FocusDirection.Next)
                        true
                    }
                    if (text.isEmpty() && it.key == Key.Backspace) {
                        focusManager.moveFocus(FocusDirection.Previous)
                    }
                    false
                },
            textStyle = LocalTextStyle.current.copy(
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                textAlign = TextAlign.Center
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = if (keyboardAction == null) ImeAction.Next else ImeAction.Done
            ),
            colors = TextFieldDefaults.colors(
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent
            ),
        )
        if (!isFull) {
            Image(painter = painter, contentDescription = "")
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OTPView(modifier: Modifier = Modifier, navController: NavController? = null,viewModel: OtpViewModel) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val (item1, item2, item3, item4, item5, item6) = FocusRequester.createRefs()
    Box(
        modifier, contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            OtpChar(
                modifier = Modifier
                    .focusRequester(item1)
                    .focusProperties {
                        next = item2
                        previous = item1
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(0,it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item2)
                    .focusProperties {
                        next = item3
                        previous = item1
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(1,it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item3)
                    .focusProperties {
                        next = item4
                        previous = item2
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(2,it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item4)
                    .focusProperties {
                        previous = item3
                        next = item5
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(3,it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item5)
                    .focusProperties {
                        previous = item4
                        next = item6
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(4,it)
                }
            )
            OtpChar(
                modifier = Modifier
                    .focusRequester(item6)
                    .focusProperties {
                        previous = item5
                        next = item6
                    },
                onTextChange = {
                    viewModel.updateOtpAtIndex(5,it)
                },
                keyboardAction = KeyboardActions(
                    onDone = {
                        viewModel.verifyOtp(context){
                            navController?.navigate(Screen.SetPasswordScreen.route)
                        }

                    }
                )
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val interactionSource = remember { MutableInteractionSource() }
    val backPainter = painterResource(id = R.drawable.back_btn)
    val helpPainter = painterResource(id = R.drawable.help_icon)
    Row(
        Modifier
            .fillMaxWidth()
            .height(80.dp),
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
                    .size(32.dp)
                    .padding(2.dp)
            )

        }
    }
}