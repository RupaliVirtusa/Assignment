package com.assignment.codingassignment.repository

import com.assignment.codingassignment.domain.Recipe
import com.assignment.codingassignment.network.ReceipeState
import com.example.hiltwithmvvm.network.responses.RecipeSearchResponse


interface RecipeRepository {
    suspend fun search(token: String, page: Int, query: String): ReceipeState<List<Recipe>>
}