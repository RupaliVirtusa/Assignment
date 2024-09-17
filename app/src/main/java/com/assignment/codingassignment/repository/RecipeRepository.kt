package com.assignment.codingassignment.repository

import androidx.lifecycle.MutableLiveData
import com.assignment.codingassignment.network.RecipeListState


interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): MutableLiveData<RecipeListState>
}