package com.assignment.codingassignment.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.assignment.codingassignment.AssignmentApplication
import com.assignment.codingassignment.BuildConfig
import com.assignment.codingassignment.UtilityTest
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import com.assignment.codingassignment.presentation.RecipeListViewModel
import com.assignment.codingassignment.repository.RecipeRepository
import com.assignment.codingassignment.repository.RecipeRepositoryImpl
import com.assignment.codingassignment.util.MainCoroutineRule
import com.assignment.codingassignment.util.getOrAwaitValueTest
import com.google.common.truth.Truth
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
@HiltAndroidTest
class RecipeViewModelTest {

    lateinit var repository: RecipeRepository

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RecipeListViewModel

    @Before
    fun setup() {
        val context = AssignmentApplication()
        repository = RecipeRepositoryImpl(UtilityTest.getRecipeService())
        viewModel = RecipeListViewModel(repository, BuildConfig.APP_TOKEN, context)
    }


    @ExperimentalCoroutinesApi
    @Test
    fun fetchData_returnsExpectedData() {
        val expectedData = RecipeSearchResponse(
            count = 30,
            recipes = listOf()
        )
        viewModel.getAllRecipes()
        val recipeList = viewModel.alRecipeList.getOrAwaitValueTest()
        recipeList.apply {
            when (this) {
                is RecipeListState.RecipeListLoaded -> {
                    Truth.assertThat(response).isNotNull()
                    Truth.assertThat(response.recipes.size)
                        .isEqualTo(expectedData.count)
                }

                else -> {

                }
            }
        }
    }
}