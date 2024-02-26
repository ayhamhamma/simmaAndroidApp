package com.simma.simmaapp.presentation.registrationView

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
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

@Preview(showBackground = true)
@Composable
fun AppBar() {
    val interactionSource = remember { MutableInteractionSource() }
    ((LocalContext.current) as LoginActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
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
                contentDescription = "back button",
                modifier = Modifier
                    .size(32.dp)
                    .padding(2.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreen(navController: NavController? = null) {
    val viewModel: RegistrationViewModel = hiltViewModel()
    val interactionSource = remember { MutableInteractionSource() }

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(start = PADDING, end = PADDING, bottom = 30.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            AppBar()
            Spacer(modifier = Modifier.size(10.dp))
            Text(
                text = "Enter Your Mobile Number",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text(
                text = "We’ll send you a code to your number so you can create your account  ",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(400),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(26.dp))
            Row {
                Box(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(1f)

                        .border(
                            1.dp, BorderColor,
                            RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "+964",
                        fontSize = 20.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(400),
                        textAlign = TextAlign.Center,
                        color = MedDarkGrey,
                    )
                }

                Spacer(modifier = Modifier.size(12.dp))
                OutlinedTextField(
                    modifier = Modifier
                        .height(48.dp)
                        .weight(3f),
                    onTextChange = {
                        viewModel.phoneNumber = it
                    },
                    text = viewModel.phoneNumber
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
                fontFamily = FontFamily(Font(R.font.font)),
                fontSize = 20.sp,
                fontWeight = FontWeight(700),
                color = MedDarkGrey,
            )
            Spacer(modifier = Modifier.size(9.dp))
            Row(Modifier.fillMaxWidth()) {
                SmsOrWhatsApp(
                    Modifier
                        .weight(1f)
                        .clickable(onClick = {
                            viewModel.textMessageSelected = true
                            viewModel.whatsAppSelected = false
                        },
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }

                        ),
                    selected =
                    viewModel.textMessageSelected
                )
                Spacer(modifier = Modifier.size(16.dp))
                SmsOrWhatsApp(
                    Modifier
                        .weight(1f)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            viewModel.textMessageSelected = false
                            viewModel.whatsAppSelected = true
                        }, selected =
                    viewModel.whatsAppSelected, painter = painterResource(
                        id = R.drawable.whats_app_icon
                    ), text = "WhatsApp"
                )
            }
        }
        BottomScreen(navController = navController, viewModel = viewModel)
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "",
    onTextChange: (String) -> Unit,
    text: String
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.White),
        onValueChange = { newText ->
            onTextChange(newText)
        },

        modifier = modifier
            .height(40.dp)
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
}

@Preview(showBackground = true)
@Composable
fun SmsOrWhatsApp(
    modifier: Modifier = Modifier,
    painter: Painter = painterResource(id = R.drawable.sms_icon),
    text: String = "Text Message",
    selected: Boolean = true,
    iconShow: Boolean= true,
    fontSize:TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Medium,
    height : Dp =48.dp

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
        if(iconShow){
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
    viewModel: RegistrationViewModel
) {
    val context = LocalContext.current
    Column(modifier = modifier) {
        Row {
            Text(
                text = "By clicking continue, you agree to our ",
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
                    viewModel.onButtonClick(context) {
                        navController?.navigate(Screen.OtpScreen.route)
                    }
                }, contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Continue",
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