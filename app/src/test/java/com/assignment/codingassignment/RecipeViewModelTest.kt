package com.assignment.codingassignment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.model.RecipeDto
import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import com.assignment.codingassignment.presentation.RecipeListViewModel
import com.assignment.codingassignment.repository.RecipeRepository
import com.assignment.codingassignment.utils.Constants
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@RunWith(AndroidJUnit4::class)
class RecipeViewModelTest {

    @Mock
    lateinit var repository: RecipeRepository

    @Mock
    lateinit var observer: Observer<List<RecipeDto>>

    private lateinit var viewModel: RecipeListViewModel
    private val context: Context = ApplicationProvider.getApplicationContext()

    @Before
    fun setup() {
        viewModel = RecipeListViewModel(repository, Constants.APP_TOKEN)
    }

    @Test
    fun fetchData_returnsExpectedData() = runBlocking {
        var expectedData = MutableLiveData<RecipeListState>()

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