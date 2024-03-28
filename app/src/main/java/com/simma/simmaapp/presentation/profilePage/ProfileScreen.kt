package com.simma.simmaapp.presentation.profilePage

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.simma.simmaapp.R
import com.simma.simmaapp.model.citiesModel.CitiesModelItem
import com.simma.simmaapp.presentation.deleveryAndPayment.MenuItem
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.theme.ActiveResendCodeColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.LightGrey
import com.simma.simmaapp.presentation.theme.MedDarkGrey

@Preview(showBackground = true)
@Composable
fun ProfileScreen(navController: NavController? = null) {
    val viewModel: ProfileScreenViewModel = hiltViewModel()
    Box {
        Column(
            Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(PADDING)
        ) {
            var isExpanded by remember {
                mutableStateOf(false)
            }
            Spacer(modifier = Modifier.size(12.dp))
            Text(
                text = "Tell Us a little\nabout yourself! 👋",
                style = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = MedDarkGrey,
                )
            )
            Spacer(modifier = Modifier.size(21.dp))
            Row(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        isError = viewModel.isFirstNameError,
                        text = viewModel.nameText,
                        onTextChange = {
                            viewModel.nameText = it
                        }
                    )
                }
                Spacer(modifier = Modifier.size(14.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        // Other TextField attributes...
                        hint = "Last Name",
                        isError = viewModel.isLastNameError,
                        text =
                        viewModel.lastName,
                        onTextChange = {
                            viewModel.lastName = it
                        }
                    )
                }
            }
            Spacer(modifier = Modifier.size(14.dp))
            Box(
                modifier = Modifier
                    .height(40.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxSize(),
                    // Other TextField attributes...
                    icon = R.drawable.phone_field_icon,
                    hint = "Phone",
                    text = viewModel.phoneText,
                    onTextChange = {
//                            viewModel.phoneText = it
                    }
                )
            }

            if(false){
                Spacer(modifier = Modifier.size(14.dp))
                Row(Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        icon = R.drawable.gender_icon,
                        hint = "Gender",
                        text = "",
//                        viewModel.genderText,
                        onTextChange = {
//                            viewModel.genderText = it
                        }
                    )
                }
                Spacer(modifier = Modifier.size(14.dp))
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(40.dp)
                ) {
                    OutlinedTextField(
                        modifier = Modifier.fillMaxSize(),
                        // Other TextField attributes...
                        icon = R.drawable.date_of_birth,
                        hint = "Date of Birth",
                        text = "",
//                        viewModel.dateOfBirthText,
                        onTextChange = {
//                            viewModel.dateOfBirthText = it
                        }
                    )
                }
            }
            }
            Spacer(modifier = Modifier.size(14.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                icon = R.drawable.email_icon,
                hint = "Email",
                text = viewModel.emailText,
                onTextChange = {
                    viewModel.emailText = it
                }
            )
            Spacer(modifier = Modifier.size(14.dp))
            Box {
                CustomDropDownMenu(
                    list = viewModel.citiesList,
                    isExpanded = isExpanded,
                    onChangeExpanding = { isExpanded = !isExpanded },
                    isError = viewModel.isCityError,
                    viewModel = viewModel
                )

            }
            Spacer(modifier = Modifier.size(14.dp))
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(72.dp),
                icon = R.drawable.detaild_address_icon,
                hint = "Address Details",
                isLong = true,
                isError =
                viewModel.isAddressError,
                text =
                    viewModel.addressText,
                onTextChange = {
                    viewModel.addressText = it
                }
            )
            Spacer(modifier = Modifier.size(28.dp))
            SaveButton(isLoading = viewModel.isButtonLoading) {
                viewModel.onSaveClick(navController!!)
            }

        }

        Text(
            text = "Delete Account",
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.font_med)),
                fontWeight = FontWeight(860),
                color = Color(0xFFAFAFAF),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
            ),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 16.dp, bottom = 33.dp)
        )
        if(viewModel.isLoading)
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White), contentAlignment = Alignment.Center) {
            val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))
            LottieAnimation(
                composition = composition,
                iterations = LottieConstants.IterateForever,
                modifier = Modifier
                    .height(60.dp)
                    .width(100.dp)
            )
        }
    }


}

// outlined top start gravity no hint animation outlined text field
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun OutlinedTextField(
    modifier: Modifier = Modifier,
    hint: String = "Name",
    onTextChange: ((String) -> Unit)? = null,
    text: String = "",
    @DrawableRes icon: Int = R.drawable.name_icon,
    isError: Boolean = false,
    errorMessage: String = "",
    isLong: Boolean = false
) {
    val interactionSource = remember { MutableInteractionSource() }

    Column {
        BasicTextField(
            value = text,
            singleLine = false, // Set singleLine to false for multi-line support
            interactionSource = interactionSource,
            cursorBrush = SolidColor(Color.White),
            onValueChange = { newText ->
                onTextChange?.invoke(newText)
            },
            modifier = modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.White)
                .height(40.dp)
        ) { innerTextField ->
            OutlinedTextFieldDefaults.DecorationBox(
                value = text,
                innerTextField = innerTextField,
                enabled = true,
                singleLine = false,
                contentPadding = PaddingValues(
                    start = 10.dp,
                    end = 10.dp,
                    top = 10.dp,
                    bottom = 10.dp
                ),
                interactionSource = interactionSource,
                visualTransformation = VisualTransformation.None,
                leadingIcon = @Composable {
                    if (isLong) {
                        Box(modifier = Modifier.fillMaxHeight()) {
                            Icon(
                                painter = painterResource(id = icon),
                                contentDescription = "",
                                Modifier
                                    .padding(top = 15.dp)
                                    .size(15.dp),
                                tint = if(text.isEmpty()) LightGrey else ActiveResendCodeColor
                            )
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = icon),
                            contentDescription = "",
                            Modifier
                                .size(15.dp),
                            tint = if(text.isEmpty()) LightGrey else ActiveResendCodeColor
                        )
                    }

                },
                placeholder = {
                    Text(
                        text = hint,
                        style = TextStyle(
                            textAlign = TextAlign.Start,
                            color = LightGrey
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
                            unfocusedBorderColor = LightGrey,
                            focusedLabelColor = Yellow,
                            unfocusedLabelColor = LightGrey,
                            errorBorderColor = ErrorColor,
                            errorLabelColor = ErrorColor
                        ),
                        shape = RoundedCornerShape(10.dp),
                        focusedBorderThickness = 1.dp,
                        unfocusedBorderThickness = 1.dp,
                    )
                }
            )
        }

        if (isError) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 10.dp, top = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error_worning),
                    contentDescription = "Invalid or inapplicable discount code",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Field is required",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(300),
                        color = ErrorColor,
                    ),

                    )
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDropDownMenu(
    modifier: Modifier = Modifier,
    list: List<CitiesModelItem>,
    isExpanded: Boolean,
    onChangeExpanding: () -> Unit,
    isError: Boolean = true,
    viewModel: ProfileScreenViewModel
) {
    Column {
        Box(modifier = modifier.height(40.dp), contentAlignment = Alignment.Center) {

            ExposedDropdownMenuBox(expanded = isExpanded, onExpandedChange = {
                onChangeExpanding()
            }) {
                Column(Modifier) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(10.dp))
                            .border(
                                1.dp,
                                if (isError) ErrorColor else LightGrey, RoundedCornerShape(10.dp)
                            )
                            .height(40.dp)
                            .menuAnchor(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Spacer(modifier = Modifier.size(15.dp))
                        Image(
                            painter = painterResource(id = R.drawable.cities_icon),
                            contentDescription = "",
                            Modifier.size(15.dp)

                        )
                        Spacer(modifier = Modifier.size(15.dp))
                        Text(
                            text = viewModel.deliveryCityName,
                            style = TextStyle(
                                textAlign = TextAlign.Start,
                                color = LightGrey
                            )
                        )
                    }

                }

                ExposedDropdownMenu(
                    expanded = isExpanded,
                    onDismissRequest = { onChangeExpanding() },
                    Modifier
                        .background(Color.White)
                        .height(300.dp)
                ) {
                    list.forEach { item ->
                        MenuItem(item) {
                            viewModel.deliveryCity = item.id
                            viewModel.deliveryCityName = it
                            onChangeExpanding()
                        }
                    }
                }

                Image(
                    painter = if (isExpanded) painterResource(id = R.drawable.open_drop_down) else painterResource(
                        id = R.drawable.clased_drop_done
                    ), contentDescription = "DropDown",
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(32.dp)
                        .align(Alignment.CenterEnd)
                )
            }
        }
        if (isError) {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(end = 10.dp, top = 5.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.error_worning),
                    contentDescription = "Invalid or inapplicable discount code",
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.size(5.dp))
                Text(
                    text = "Field is required",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 24.sp,
                        fontFamily = FontFamily(Font(R.font.font_med)),
                        fontWeight = FontWeight(300),
                        color = ErrorColor,
                    ),

                    )
            }
        }
    }

}

@Composable
fun SaveButton(modifier: Modifier = Modifier,isLoading : Boolean, text: String = "Save", onClick: () -> Unit, ) {
    Box(modifier.fillMaxWidth(),contentAlignment = Alignment.Center){
        Box(
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Yellow)
                .height(39.dp)
                .clickable {
                    onClick.invoke()
                }, contentAlignment = Alignment.Center
        ) {
            AnimatedVisibility(visible = !isLoading, exit = shrinkHorizontally(), enter = expandHorizontally()) {
                Text(
                    text = text,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight(1000),
                        color = Color(0xFF2D2D2D),
                        textAlign = TextAlign.Center
                    ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AnimatedVisibility(visible = isLoading, enter = fadeIn(), exit = fadeOut()) {
                val composition by rememberLottieComposition(spec = LottieCompositionSpec.RawRes(R.raw.loading_animation))
                LottieAnimation(
                    composition = composition,
                    iterations = LottieConstants.IterateForever,
                    modifier = Modifier
                        .height(30.dp)
                        .width(100.dp)
                )
            }
        }
    }


}