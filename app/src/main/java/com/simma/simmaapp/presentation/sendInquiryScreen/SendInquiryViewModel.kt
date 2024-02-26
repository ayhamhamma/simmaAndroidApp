package com.simma.simmaapp.presentation.sendInquiryScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.simma.simmaapp.model.getInquiryCategoriesModel.InquiriesCategoriesItem
import com.simma.simmaapp.model.sendInquiry.SendInquiryRequestBody
import com.simma.simmaapp.presentation.homeScreen.HomeScreens
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Helpers.getToken
import com.simma.simmaapp.utils.Helpers.getUserId
import com.simma.simmaapp.utils.Helpers.showMessage
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
@SuppressLint("StaticFieldLeak")
class SendInquiryViewModel @Inject constructor (
    val repository: Repository,
     @ApplicationContext var  context: Context
) : ViewModel() {

    var state by mutableStateOf(
        PageState(
            listOf(),
            "",
            false,
            "",
            "What do you need help with?"
        )
    )

    init {
        getCategories()
    }

    private fun getCategories() {
        viewModelScope.launch {
            repository.getInquiryCategories().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        state = state.copy(
                            list =  result.data
                        )
                    }

                    is Resource.Error ->
                        Unit

                    is Resource.Loading ->
                        Unit

                }
            }
        }
    }

    fun sendInquiry(navController: NavController){
        if(state.inquiryQuestionText.isEmpty() || state.inquiryQuestionText.isBlank()){
            state = state.copy(isTextBoxError = true)
            return
        }
        if(state.selectedCategoryId.isEmpty()|| state.selectedCategoryId.isBlank()){
            showMessage(context,"Please Choose Inquiry Category.")
            return
        }
        viewModelScope.launch {
            val data = SendInquiryRequestBody(categoryId = state.selectedCategoryId, createdBy = getUserId(context = context), desc = state.inquiryQuestionText)
            repository.sendInquiry(data,getToken(context)).collect{
                result ->
                when (result) {
                    is Resource.Success -> {
                        showMessage(context,"Your inquiry was received successfully. Our staff will be in contact with you.")
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.popBackStack()
                        navController.navigate(HomeScreens.HomeScreen.route)
                    }

                    is Resource.Error ->
                        Unit

                    is Resource.Loading ->
                        Unit

                }
            }
        }
    }

    fun updateChosenItem(itemName: String, itemId: String) {
        state = state.copy(
            selectedCategoryId = itemId,
            selectedCategoryText = itemName
        )
    }

    fun updateQuestionText(text: String) {
        state = state.copy(
            inquiryQuestionText = text,
            isTextBoxError = false
        )
    }

}

data class PageState(
    val list : List<InquiriesCategoriesItem>,
    val inquiryQuestionText : String,
    val isTextBoxError : Boolean,
    val selectedCategoryId : String,
    val selectedCategoryText: String
)