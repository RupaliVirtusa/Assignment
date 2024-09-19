package com.assignment.codingassignment.presentation

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.codingassignment.R
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.repository.RecipeRepository
import com.assignment.codingassignment.utils.NetworkHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    @Named("auth_token") private val token: String,
    private val app: Application
) : ViewModel() {
    val alRecipeList = MutableLiveData<RecipeListState>()

    var job: Job? = null
    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    private fun onError(s: String) {
        alRecipeList.value = RecipeListState.Error(s)
    }

    init {
        getAllRecipes()
    }

    fun getAllRecipes() {
        if (NetworkHelper.isNetworkAvailable(app)) {
            job = viewModelScope.launch(exceptionHandler) {
                val result = repository.search(token = "token", page = 1, query = "Chicken")
                alRecipeList.value = result.value
            }
        } else {
            alRecipeList.value = RecipeListState.Error(app.getString(R.string.no_internet))
        }

    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}