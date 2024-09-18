package com.assignment.codingassignment

import androidx.lifecycle.MutableLiveData
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.RecipeService
import com.assignment.codingassignment.repository.RecipeRepositoryImpl
import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@HiltAndroidTest
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
    fun searchTest() {
        runBlocking {
            var recipeState = MutableLiveData<RecipeListState>()

            recipeState.value = RecipeListState.RecipeListLoaded(
                RecipeSearchResponse(
                    count = 10,
                    recipes = listOf()
                )
            )

            Mockito.`when`(
                recipeRepository.search(
                    "Constants.APP_TOKEN",
                    1,
                    "Chicken"
                )
            ).thenReturn(recipeState)


        /* val response = apiService.search(
                "Constants.APP_TOKEN",
                1,
                "Chicken"
            )
            assertEquals(
                listOf<RecipeDto>(),
                response.body()?.let { RecipeListState.RecipeListLoaded(response = it) }
            )*/
        }
    }
}

