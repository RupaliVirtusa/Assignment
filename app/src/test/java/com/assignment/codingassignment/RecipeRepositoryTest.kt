package com.assignment.codingassignment

import com.assignment.codingassignment.domain.Recipe
import com.assignment.codingassignment.network.Constants
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.RecipeService
import com.assignment.codingassignment.network.model.RecipeDto
import com.assignment.codingassignment.repository.RecipeRepository
import com.assignment.codingassignment.repository.RecipeRepositoryImpl
import com.example.hiltwithmvvm.network.responses.RecipeSearchResponse
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class RecipeRepositoryTest {
    lateinit var recipeRepository: RecipeRepositoryImpl

    @Mock
    lateinit var apiService: RecipeService

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        recipeRepository = RecipeRepositoryImpl(apiService)
    }

    @Test
    suspend fun searchTest() {
        Mockito.`when`(
            apiService.search(
                Constants.APP_TOKEN,
                1,
                "Chicken"
            )
        ).thenReturn(Response.success(RecipeSearchResponse(10, listOf<RecipeDto>())))
        val response = apiService.search(
            Constants.APP_TOKEN,
            1,
            "Chicken"
        )
        assertEquals(
            listOf<RecipeDto>(),
            response.body()?.let { RecipeListState.RecipeListLoaded(response = it) }
        )
    }
}