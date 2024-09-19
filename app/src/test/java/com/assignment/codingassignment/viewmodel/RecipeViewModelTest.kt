package com.assignment.codingassignment.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.codingassignment.BuildConfig
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.model.RecipeDto
import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import com.assignment.codingassignment.presentation.RecipeListViewModel
import com.assignment.codingassignment.repository.RecipeRepository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class RecipeViewModelTest {

    @Mock
    lateinit var repository: RecipeRepository

    @Mock
    lateinit var observer: Observer<List<RecipeDto>>

    private lateinit var viewModel: RecipeListViewModel

    @Before
    fun setup() {
        viewModel = RecipeListViewModel(repository, BuildConfig.APP_TOKEN)
    }

    @Test
    fun fetchData_returnsExpectedData() = runBlocking {
        val expectedData = MutableLiveData<RecipeListState>()

        expectedData.value = RecipeListState.RecipeListLoaded(
            RecipeSearchResponse(
                count = 10,
                recipes = listOf()
            )
        )
        Mockito.`when`(
            repository.search(
                "Constants.APP_TOKEN",
                1,
                "Chicken"
            )
        ).thenReturn(expectedData)

        viewModel.getAllRecipes()
        verify(observer).onChanged((expectedData.value as RecipeListState.RecipeListLoaded).response.recipes)

    }

}