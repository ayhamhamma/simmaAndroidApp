package com.simma.simmaapp.presentation.sendInquiryScreen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
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
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.billythelittle.lazycolumns.LazyColumnScrollbarSettings
import com.billythelittle.lazycolumns.LazyColumnWithScrollbar
import com.simma.simmaapp.R
import com.simma.simmaapp.model.getInquiryCategoriesModel.InquiriesCategoriesItem
import com.simma.simmaapp.presentation.homePage.HomeActivity
import com.simma.simmaapp.presentation.homePage.ui.theme.Yellow
import com.simma.simmaapp.presentation.theme.CheckoutAppBarColor
import com.simma.simmaapp.presentation.theme.Dimen.PADDING
import com.simma.simmaapp.presentation.theme.DropDownTextColor
import com.simma.simmaapp.presentation.theme.ErrorColor
import com.simma.simmaapp.presentation.theme.LightGrey
import com.simma.simmaapp.presentation.theme.MedDarkGrey

@Composable
fun SendInquiryScreen(navController: NavController) {
    var isExposed by remember {
        mutableStateOf(false)
    }
    val viewModel: SendInquiryViewModel = hiltViewModel()
    Box(
        Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        AppBar()
        Row(
            Modifier
                .fillMaxWidth()
                .padding(top = 96.dp, start = PADDING, end = PADDING)
                .height(40.dp)
                .background(color = CheckoutAppBarColor, shape = RoundedCornerShape(size = 4.dp))
                .clickable {
                    isExposed = !isExposed
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = viewModel.state.selectedCategoryText,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.font_med)),
                    fontWeight = FontWeight(700),
                    color = MedDarkGrey,
                ),
                modifier = Modifier.padding(8.dp)
            )
            Image(
                painter = painterResource(id = if (isExposed) R.drawable.open_drop_down else R.drawable.clased_drop_done),
                contentDescription = "Open Help Options",
                modifier = Modifier.padding(8.dp)
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 144.dp, start = PADDING, end = PADDING)
                .height(90.dp),
            onTextChange = {
                viewModel.updateQuestionText(it)
            }, text = viewModel.state.inquiryQuestionText,
            isError = viewModel.state.isTextBoxError
        )
        SaveButton(
            Modifier
                .fillMaxWidth()
                .padding(start = PADDING, end = PADDING, top = 259.dp), "Send") {
            viewModel.sendInquiry(navController)

        }
        CustomExposedDropDownMenu(
            isExposed = isExposed,
            modifier = Modifier.padding(top = 144.dp, start = PADDING, end = PADDING),
            viewModel = viewModel,
            dismiss = {
                isExposed = false
            },
            onItemChoose = {
                isExposed = false
            }
        )



    }

}

@Composable
fun CustomExposedDropDownMenu(
    modifier: Modifier = Modifier,
    isExposed: Boolean = true,
    viewModel: SendInquiryViewModel,
    dismiss: () -> Unit = {},
    onItemChoose: () -> Unit = {}
) {
    var isListExposed by remember {
        mutableStateOf(isExposed)
    }
    val scrollbarSettings = remember {
        mutableStateOf(LazyColumnScrollbarSettings())
    }
    scrollbarSettings.value = scrollbarSettings.value.copy(
        thumbColor = DropDownTextColor,
        thumbWidth = LazyColumnScrollbarSettings.ThumbWidth.X_SMALL,
        thumbHeight = LazyColumnScrollbarSettings.ThumbHeight.X_SMALL
    )
    val interactionSource = remember { MutableInteractionSource() }
    AnimatedVisibility(
        visible = isExposed,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        DisposableEffect(Unit) {
            isListExposed = isExposed
            onDispose {
                isListExposed = false
            }
        }
        Box(
            modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                    // do not remove this
                ) {


                    dismiss()
                }) {
            AnimatedVisibility(
                visible = isListExposed,
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = CheckoutAppBarColor

                    ),
                    border = BorderStroke(1.dp, DropDownTextColor),
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 6.dp
                    )
                ) {

                    LazyColumnWithScrollbar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(max = 338.dp),
                        settings = scrollbarSettings.value,
                        data = viewModel.state.list
                    ) {
                        items(
                            viewModel.state.list
                        ) {
                            MenuItem(it) { itemName, itemId ->
                                onItemChoose()
                                viewModel.updateChosenItem(itemName, itemId)
                            }
                        }
                    }
                }
            }
        }


    }
}

@Composable
fun MenuItem(
    item: InquiriesCategoriesItem,
    onItemSelected: (itemName: String, itemId: String) -> Unit
) {
    Box(
        Modifier
            .fillMaxWidth()
            .clickable {
                onItemSelected(item.name.en, item.id)
            }
//            .background(Color.White)
    ) {
        Text(
            text = item.name.en,
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 24.sp,
                fontFamily = FontFamily(Font(R.font.font)),
                fontWeight = FontWeight(400),
                color = DropDownTextColor,

                ),
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp)
                .align(Alignment.CenterStart)
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomStart)
                .alpha(0.2f)
                .padding(start = 10.dp, top = 46.dp, end = 10.dp)
                .height(0.8.dp)
                .background(DropDownTextColor)
        )
    }
}

// outlined top start gravity no hint animation outlined text field
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedTextField(
    modifier: Modifier,
    hint: String = "Inquiry or Issue Details ",
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
        textStyle = TextStyle(textDirection = TextDirection.Content),
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .background(Color.White)
    ) { innerTextField ->
        OutlinedTextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
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
                        errorLabelColor = ErrorColor
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
fun AppBar() {
    val interactionSource = remember { MutableInteractionSource() }

    val painter = painterResource(id = R.drawable.back)
    val context = LocalContext.current
    Row(
        Modifier
            .fillMaxWidth()
            .padding(start = PADDING, end = PADDING)
            .background(Color.White)
            .height(80.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                painter = painter,
                contentDescription = "Home",
                modifier = Modifier
                    .size(20.dp)
                    .clickable(
                        interactionSource = interactionSource,
                        indication = null
                    ) {
                        (context as HomeActivity).onBackPressedDispatcher.onBackPressed()
                    },
                tint = MedDarkGrey
            )
        }
    }
}

@Composable
fun SaveButton(modifier: Modifier = Modifier, text: String = "Save", onClick: () -> Unit) {
    Box(
        modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Yellow)
            .fillMaxWidth()
            .height(39.dp)
            .clickable {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight(1000),
                color = Color(0xFF2D2D2D),
            )
        )
    }

}
