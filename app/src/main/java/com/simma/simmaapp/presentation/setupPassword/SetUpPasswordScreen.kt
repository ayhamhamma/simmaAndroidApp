package com.simma.simmaapp.presentation.setupPassword

import android.content.Intent
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import com.simma.simmaapp.R
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.loginScreen.LoginActivity
import com.simma.simmaapp.presentation.theme.BorderColor

@Preview(showBackground = true)
@Composable
fun SetUpPassword(navController: NavController? = null) {
    val interactionSource = remember { MutableInteractionSource() }
    ((LocalContext.current) as LoginActivity).window.apply {
        navigationBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
        statusBarColor = ContextCompat.getColor(context, R.color.statusAndNavigation)
    }
    val context = LocalContext.current
    Box(modifier = Modifier
        .fillMaxSize().background(Color.White)
        .padding(start = 30.dp, end = 30.dp)) {
        AppBar()
        Column(
            Modifier
                .fillMaxWidth()
                .align(Alignment.Center)) {
            Text(
                text = "One last step, set up your password",
                    fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                    fontWeight = FontWeight(700),
                    color = Color(0xFF4A4A4A),
            )
            Spacer(modifier = Modifier.size(16.dp))
            Box(Modifier.fillMaxWidth()) {
                val painter = painterResource(id = R.drawable.help)
                OutlinedTextField(modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp))
                Image(painter = painter, contentDescription = "help",
                    Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 16.dp))
            }
            Spacer(modifier = Modifier.size(32.dp))
            Box(
                Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Yellow)
                    .fillMaxWidth()
                    .height(56.dp).clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        context.startActivity(Intent(context,HomeActivity::class.java))
                    }, contentAlignment = Alignment.Center) {
                Text(
                    text = "Done",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font)),
                        fontWeight = FontWeight(700),
                        color = Color(0xFF2D2D2D),
                    )
                )
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun AppBar() {
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
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(modifier: Modifier, hint: String = "") {
    var searchText by rememberSaveable { mutableStateOf("") }
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = searchText,
        singleLine = true,
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.White),
        onValueChange = { newText -> searchText = newText },
        modifier = modifier
            .height(40.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = searchText,
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
                            ,fontFamily = FontFamily(Font(R.font.font)),
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