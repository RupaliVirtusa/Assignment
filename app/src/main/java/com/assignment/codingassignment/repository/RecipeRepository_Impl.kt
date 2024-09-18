package com.assignment.codingassignment.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.RecipeService
import retrofit2.Response

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
        Log.v("TAG", response.toString())
        Log.v("TAG", response.body().toString())
        if (response.isSuccessful) {
            recipeState.value = response.body()?.let { RecipeListState.RecipeListLoaded(it) }
        } else {
            recipeState.value = RecipeListState.Error(response.errorBody().toString())
        }
        return recipeState
    }

    sealed class NetworkState<out T> {
        data class Success<out T>(val data: T) : NetworkState<T>()
        data class Error<T>(val response: Response<T>) : NetworkState<T>()
    }

    fun <T> Response<T>.parseResponse(): NetworkState<T> {
        return if (this.isSuccessful && this.body() != null) {
            val responseBody = this.body()
            NetworkState.Success(responseBody!!)
        } else {
            NetworkState.Error(this)
        }
    }

}