package com.simma.simmaapp.presentation.frequentlyAskedQuestionsScreen

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simma.simmaapp.model.frequentlyAskedModel.Result
import com.simma.simmaapp.remote.Repository
import com.vs.simma.model.listingModel.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@SuppressLint("StaticFieldLeak")
@HiltViewModel
class FrequentlyAskedQuestionsViewModel @Inject constructor(
    private val repository: Repository,
    @ApplicationContext private val context: Context
):ViewModel() {
    init {
        getFrequentlyAskedQuestions()
    }

    var questionsList = mutableStateListOf<Result>()

    private fun getFrequentlyAskedQuestions() {
        viewModelScope.launch {
            repository.getFrequentlyAskedQuestions().collect{
                result ->
                when(result){
                    is Resource.Success->{
                        questionsList.clear()
                        questionsList += result.data.results
                    }
                    is Resource.Error ->{

                    }
                    is Resource.Loading->{

                    }
                    else ->{
                        // nothing here
                    }
                }
            }
        }
    }
}