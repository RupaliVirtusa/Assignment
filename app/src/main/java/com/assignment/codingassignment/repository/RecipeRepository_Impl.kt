package com.assignment.codingassignment.repository

import com.assignment.codingassignment.domain.Recipe
import com.assignment.codingassignment.network.ReceipeState
import com.assignment.codingassignment.network.RecipeService
import com.assignment.codingassignment.network.model.RecipeDtoMapper
import com.example.hiltwithmvvm.network.responses.RecipeSearchResponse

class RecipeRepository_Impl(
    private val recipeService: RecipeService,
    private val mapper: RecipeDtoMapper
) : RecipeRepository {
    override suspend fun search(
        token: String,
        page: Int,
        query: String
    ): ReceipeState<List<Recipe>> {
        val response = recipeService.search("authToken", 1, "chicken")

        return if (response.isSuccessful) {
            val responseBody = response.body()
            if (responseBody != null) {
                ReceipeState.Success(mapper.toDomainList(response.body()?.recipes!!))
            } else {
                ReceipeState.Error(response.message())
            }
        } else {
            ReceipeState.Error(response.message())
        }

    }
}