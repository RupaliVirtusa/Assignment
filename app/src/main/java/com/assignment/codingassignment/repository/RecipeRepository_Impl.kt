package com.assignment.codingassignment.repository

import androidx.lifecycle.MutableLiveData
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.RecipeService

class RecipeRepositoryImpl(
    private val recipeService: RecipeService,
) : RecipeRepository {


    override suspend fun search(
        token: String,
        page: Int,
        query: String
    ): MutableLiveData<RecipeListState> {
        var recipeState = MutableLiveData<RecipeListState>()
        recipeState.value = RecipeListState.Loading
        val response = recipeService.search(token, page, query)
        if (response.isSuccessful) {
            recipeState.value = response.body()?.let { RecipeListState.RecipeListLoaded(it) }
        } else {
            recipeState.value = RecipeListState.Error(response.errorBody().toString())
        }
        return recipeState
    }

}