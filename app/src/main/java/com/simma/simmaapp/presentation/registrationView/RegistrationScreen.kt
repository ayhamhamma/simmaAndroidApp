package com.simma.simmaapp.presentation.registrationView

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.loginScreen.Screen
import com.simma.simmaapp.presentation.theme.BorderColor
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.MedDarkGrey
import com.simma.simmaapp.presentation.theme.SelectedBodyYellow
import com.simma.simmaapp.presentation.theme.SelectedBorderYellow
import com.simma.simmaapp.presentation.theme.checkoutLightText
import com.simma.simmaapp.utils.Helpers.getSelectedCurrency
import com.simma.simmaapp.utils.Helpers.setSelectedCurrency

@Preview(showBackground = true)
@Composable
fun AppBar(navController: NavController? = null) {
    val interactionSource = remember { MutableInteractionSource() }
    ((LocalContext.current) as LoginActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val backPainter = painterResource(id = R.drawable.back_btn)
    val helpPainter = painterResource(id = R.drawable.help_icon)
    val context = LocalContext.current
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
                .clickable(
                    indication = null,
                    interactionSource = interactionSource
                ) {
                    (context as LoginActivity).onBackPressedDispatcher.onBackPressed()
                }
        )
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "العربية",
                fontSize = 16.sp,
                fontWeight = FontWeight(400),
                color = checkoutLightText,
                fontFamily = FontFamily(Font(R.font.font)),
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(12.dp)
                    )
                    .background(CheckoutAppBarColor)
                    .padding(start = 12.dp, end = 12.dp, top = 3.dp, bottom = 3.dp)
            )
            Spacer(modifier = Modifier.size(10.dp))

            Image(
                painter = helpPainter,
                contentDescription = "help",
                modifier = Modifier
                    .clickable {
                        navController?.navigate(Screen.HelpCenterScreen.route)
                    }
                    .size(32.dp)
                    .padding(2.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("SuspiciousIndentation")
@Preview(showBackground = true)
@Composable
fun RegistrationScreen(navController: NavController? = null, isForget: Boolean = false) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    LaunchedEffect(Unit) {
        viewModel.isForget = isForget
    }
    var isExpanded by remember {
        mutableStateOf(false)
    }
    val context = LocalContext.current
    var selectedCurrency by remember {
        mutableStateOf("+${getSelectedCurrency(context)}")
    }
    val interactionSource = remember { MutableInteractionSource() }
    val localFocusManager = LocalFocusManager.current

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = PADDING, end = PADDING, bottom = 30.dp)
            .pointerInput(Unit) {
                detectTapGestures(onTap = {
                    localFocusManager.clearFocus()
                })
            },
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppBar(navController)
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Enter Your Mobile Number",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = if (isForget) "We’ll send you a code to your number so you can reset your password." else "We’ll send you a code to your number so you can create your account.",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(400),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(26.dp))
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
                            setSelectedCurrency(context, "962")
                        })
                        DropdownMenuItem(text = { Text(text = "+964") }, onClick = {
                            selectedCurrency = "+964"
                            setSelectedCurrency(context, "964")
                        })
                    }
                }

                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(3f),
                    onTextChange = {
                        viewModel.phoneNumber = it
                    },
                    text = viewModel.phoneNumber,
                    isNumbers = true
                )
            }
            Spacer(modifier = Modifier.size(8.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = "Referral code",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(700),
                            color = MedDarkGrey,
                            fontFamily = FontFamily(Font(R.font.font)),

                            )
                    )
                    Text(
                        text = "(optional)",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontWeight = FontWeight(400),
                            color = MedDarkGrey,
                            fontFamily = FontFamily(Font(R.font.font)),
                        )
                    )

                }
                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(3f),
                    onTextChange = {
                        viewModel.referralText = it
                    },
                    text = viewModel.referralText
                )


            }
            Spacer(modifier = Modifier.size(27.dp))
            Text(
                text = "Specify your preferred verification method:  ",
                fontFamily = FontFamily(Font(R.font.font_bold)),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(9.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (viewModel.isTelegramEnabled)
                    item {
                        SmsOrWhatsApp(
                            Modifier
                                .weight(1f)
                                .clickable(onClick = {
                                    viewModel.textMessageSelected = false
                                    viewModel.whatsAppSelected = false
                                    viewModel.telegramSelected = true
                                },
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }

                                ),
                            painter = painterResource(id = R.drawable.telegram_logo),
                            text = "Telegram",
                            selected =
                            viewModel.telegramSelected
                        )
                    }
                if (viewModel.isWhatsAppEnabled)
                    item {
                        SmsOrWhatsApp(
                            Modifier
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    viewModel.textMessageSelected = false
                                    viewModel.whatsAppSelected = true
                                    viewModel.telegramSelected = false
                                }, selected =
                            viewModel.whatsAppSelected, painter = painterResource(
                                id = R.drawable.whats_app_icon
                            ), text = "WhatsApp"
                        )
                    }

                if (viewModel.isSMSEnabled)
                    item {
                        SmsOrWhatsApp(
                            Modifier
                                .weight(1f)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    viewModel.textMessageSelected = true
                                    viewModel.whatsAppSelected = false
                                    viewModel.telegramSelected = false
                                },
                            selected =
                            viewModel.textMessageSelected,
                        )
                    }

            }
        }
        BottomScreen(navController = navController, viewModel = viewModel, isForget = isForget)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit,
    text: String,
    isNumbers: Boolean = false

) {
    val interactionSource = remember { MutableInteractionSource() }
    val focusRequester = FocusRequester()
    var isFocused by remember { mutableStateOf(false) }
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
                if (isNumbers) {
                    if (newText.length <= 10) {
                        onTextChange(newText)
                    }
                } else {
                    onTextChange(newText)
                }


            },
            keyboardOptions = if (isNumbers) KeyboardOptions(keyboardType = KeyboardType.Phone) else KeyboardOptions.Default,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .focusRequester(focusRequester)
                .pointerInput(Unit) {
                    detectTapGestures { offset ->
                        // Check if the tap is outside the TextField
                        if (!isFocused) {
                            focusRequester.freeFocus()
                        }
                    }
                }
                .onFocusChanged {
                    isFocused = it.isFocused
                },
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
            Box(modifier = Modifier
                .padding(end = 10.dp)
                .size(18.dp)
                .align(Alignment.CenterEnd)
                .clip(CircleShape)
                .clickable {
                    onTextChange("")
                }
                .background(BorderColor),
                contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.drawable.delete_text),
                    contentDescription = "delete all phone number ",
                    Modifier
                        .padding(4.dp)
                        .size(16.dp)
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SmsOrWhatsApp(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.sms_icon),
    text: String = "Text Message",
    selected: Boolean = true,
    iconShow: Boolean = true,
    fontSize: TextUnit = 16.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    height: Dp = 48.dp

) {
    Row(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .border(
                1.dp, if (selected) SelectedBorderYellow else BorderColor,
                RoundedCornerShape(12.dp)
            )
            .height(height)
            .background(if (selected) SelectedBodyYellow else Color.Transparent),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        if (iconShow) {
            Image(painter = painter, contentDescription = text, modifier = Modifier.size(28.dp))
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
            text = text,
            fontSize = fontSize,
            fontWeight = fontWeight,
            color = MedDarkGrey,
            fontFamily = FontFamily(Font(R.font.font)),
            textAlign = TextAlign.Center,
        )
    }
}

@Composable
fun BottomScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    viewModel: RegistrationViewModel,
    isForget: Boolean
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row {
            Text(
                text = "By clicking Send Code Button, you agree to our",
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF4A4A4A),
            )
            Text(
                text = "T&C",
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight(800),
                color = Color(0xFF4A4A4A),
                modifier = Modifier.clickable {
                    navController?.navigate(Screen.TermsAndConditionsScreen.route)
                }
            )
            Text(
                text = " & ",
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color(0xFF4A4A4A),
            )
            Text(
                text = "Privacy policy",
                fontSize = 12.sp,
                textDecoration = TextDecoration.Underline,
                fontWeight = FontWeight(800),
                color = Color(0xFF4A4A4A),
                modifier = Modifier.clickable {
                    navController?.navigate(Screen.TermsAndConditionsScreen.route)
                }
            )
        }
        Spacer(modifier = modifier.size(11.dp))
        Box(
            modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Yellow)
                .fillMaxWidth()
                .height(56.dp)
                .clickable {
                    viewModel.onButtonClick(context = context, onBackPressed = {
                        (context as LoginActivity).onBackPressed()
                    },
                        navigate = {
                            navController?.navigate(Screen.OtpScreen.route + "?isForget=$isForget")
                        })
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Send Code",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 24.sp,
                    fontWeight = FontWeight(700),
                    color = Color(0xFF2D2D2D),
                )
            )
        }
    }
}