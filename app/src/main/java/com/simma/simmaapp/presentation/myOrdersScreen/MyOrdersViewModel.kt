package com.simma.simmaapp.presentation.myOrdersScreen

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.utils.Constants.ORDER_CHANGED
import com.simma.simmaapp.utils.Helpers
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MyOrdersViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext val appContext: Context
) : ViewModel() {

    var state by mutableStateOf(ScreenState())
    var isLoading by mutableStateOf(false)
    var showDialog by mutableStateOf(true)

    init {
        viewModelScope.launch {
            ORDER_CHANGED.collect {
                getLastOrders()
            }
        }
    }

    fun refresh() {
        state = state.copy(items = emptyList(), page = 1, endReached = false)
        getLastOrders()
    }

    fun getLastOrders() {
        viewModelScope.launch {
            val token = Helpers.getToken(appContext)
            if (!state.endReached) {
                if (!isLoading) {
                    repository.getMyOrders(state.page, token).collect { result ->
                        when (result) {
                            is Resource.Success -> {
                                state = state.copy(
                                    items = if (state.items.isEmpty()) result.data.result else state.items + result.data.result,
                                    page = state.page + 1,
                                    endReached = result.data.result.isNullOrEmpty()
                                )

                            }

                            is Resource.Error ->
                                Unit

                            is Resource.Loading -> {
                                isLoading = result.isLoading
                            }

                        }
                    }
                }
            }
        }
    }
}
