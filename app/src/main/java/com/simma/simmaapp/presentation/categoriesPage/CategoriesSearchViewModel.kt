package com.simma.simmaapp.presentation.categoriesPage

import android.content.Context
import android.util.Log
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.datatransport.runtime.logging.Logging.e
import com.simma.simmaapp.utils.Constants.CATEGORIES_LIST
import com.simma.simmaapp.utils.Constants.MERCHANTS_LIST
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.vs.simma.model.listingModel.Result

@HiltViewModel
class CategoriesSearchViewModel @Inject constructor(
    @ApplicationContext appContext: Context
) : ViewModel() {


    var search by mutableStateOf("")
    var categoriesList by mutableStateOf(
        mutableListOf<Category>()
    )
    var merchantsList  = mutableStateListOf<Result>()
    var searchState by mutableStateOf(
        true
    )
    init {
        categoriesList = CATEGORIES_LIST
        MERCHANTS_LIST.forEach {
            merchantsList.add(it)
        }
    }
    private var job : Job? = null
    fun changeSearchText(text : String){
        job?.cancel()
        search = text

        job = viewModelScope.launch {
            delay(500)
            searchState = !searchState
            if(search.isNotEmpty()){
                Log.e("ayham", "notEmpty")
                merchantsList.clear()
                MERCHANTS_LIST.filter {
                    it.name.ar.contains(search, ignoreCase = true)||it.name.en.contains(search, ignoreCase = true)
                }.forEach{
                    result ->
                    merchantsList.add(result)
                }
                Log.e("ayham", "MERCHANTS_LIST AFTER SEARCH: $MERCHANTS_LIST")
                Log.w("ayham", "merchantsList AFTER SEARCH: $merchantsList")

            }else{
                merchantsList.clear()
                MERCHANTS_LIST.forEach{result ->
                    merchantsList.add(result)
                }
                Log.e("ayham", "MERCHANTS_LIST AFTER empty SEARCH: $MERCHANTS_LIST")
                Log.w("ayham", "merchantsList AFTER empty SEARCH: $merchantsList")
            }
        }
    }


}