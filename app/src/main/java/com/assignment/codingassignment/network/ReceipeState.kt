package com.assignment.codingassignment.network

import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import retrofit2.Response

sealed class ReceipeState<out T> {
    data class Success<out T>(val data: T) : ReceipeState<T>()
    data class Error<T>(val response: Response<T>) : ReceipeState<T>()
}

sealed class RecipeListState {
    data class RecipeListLoaded(val response: RecipeSearchResponse) : RecipeListState()
    data class Error(val message: String) : RecipeListState()
    object Loading : RecipeListState()
}

fun <T> Response<T>.parseResponse(): ReceipeState<T> {
    return if (this.isSuccessful && this.body() != null) {
        val responseBody = this.body()
        ReceipeState.Success(responseBody!!)
    } else {
        ReceipeState.Error(this)
    }
}