package com.assignment.codingassignment.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment.codingassignment.domain.Recipe
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.repository.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val repository: RecipeRepository,
    private @Named("auth_token") val token: String
) : ViewModel() {


    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage
    val recipeList = MutableLiveData<List<Recipe>?>()

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

    private fun getAllRecipes() {
        job = viewModelScope.launch(exceptionHandler) {
            val result = repository.search(token = token, page = 1, query = "Chicken")
            alRecipeList.value = result.value
        }
    }


    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}