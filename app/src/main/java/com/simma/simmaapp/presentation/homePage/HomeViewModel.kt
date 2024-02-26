package com.simma.simmaapp.presentation.homePage

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.remote.Repository
import com.vs.simma.model.listingModel.Resource
import com.vs.simma.model.listingModel.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel@Inject constructor(
    private val repository: Repository
):ViewModel() {
    // Use a mutable state variable for storesList
    var storesList by mutableStateOf<List<Result>>(emptyList())
        private set

    init {
        getStoreListings()
    }
    // Handel API response
    private fun getStoreListings() {
        viewModelScope.launch {
            repository.getStoresListings().collect { result ->
                when (result) {
                    is Resource.Success -> {
                        storesList = result.data.results
                    }

                    is Resource.Error -> {
                        Unit
                    }

                    is Resource.Loading -> {
                        Unit
                    }
                }
            }
        }
    }
}