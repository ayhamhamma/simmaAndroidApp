package com.simma.simmaapp.presentation.testModule

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.LightGrey

@Preview
@Composable
fun testScreen() {
    var value by remember { mutableStateOf("") }

    Box (Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        OutlinedTextField2(text = value, modifier = Modifier.fillMaxWidth(), onTextChange = {
            value = it
        })
//        OutlinedTextField(value = value, onValueChange = {
//            value = it
//        })
    }
}

// outlined top start gravity no hint animation outlined text field
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField2(
    modifier: Modifier,
    hint: String = "Detailed Address",
    onTextChange: (String) -> Unit,
    text: String,
    isError: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    BasicTextField(
        value = text,
        singleLine = false, // Set singleLine to false for multi-line support
        interactionSource = interactionSource,
        cursorBrush = SolidColor(Color.Black),
        onValueChange = { newText ->
            onTextChange(newText)
        },
        textStyle = TextStyle(
            textDirection = TextDirection.Content

        ),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White),
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
            colors = OutlinedTextFieldDefaults.colors(
                cursorColor = Color.Black
            ),
            enabled = true,
            singleLine = false,
            contentPadding = PaddingValues(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
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
                    isError = isError,
                    interactionSource = interactionSource,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Yellow, // You can adjust the color as needed
                        unfocusedBorderColor = LightGrey,
                        focusedLabelColor = Yellow,
                        unfocusedLabelColor = LightGrey,
                        errorBorderColor = ErrorColor,
                        errorLabelColor = ErrorColor,
                        cursorColor = Color.Black,
                    ),
                    shape = RoundedCornerShape(10.dp),
                    focusedBorderThickness = 1.dp,
                    unfocusedBorderThickness = 1.dp,
                )
            }
        )
    }
}