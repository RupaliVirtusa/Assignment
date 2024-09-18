package com.assignment.codingassignment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.assignment.codingassignment.network.RecipeListState
import com.assignment.codingassignment.network.RecipeService
import com.assignment.codingassignment.network.model.RecipeDto
import com.assignment.codingassignment.repository.RecipeRepositoryImpl
import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import com.assignment.codingassignment.util.UtilityTest
import com.google.gson.Gson
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@HiltAndroidTest
class RecipeRepositoryTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Mock
    lateinit var recipeTestService: RecipeTestService

    @Mock
    lateinit var context: Context

    @Before
    fun setup() {
        hiltRule.inject()

    }

    @Test
    fun searchTest() {
        val json = UtilityTest.loadJSONFromAsset(context, "recipe.json")
        val recipeResponse = Gson().fromJson(json, RecipeSearchResponse::class.java)

        runBlocking {
            Mockito.`when`(
                recipeTestService.search("token", 1, "query")
            ).thenReturn(recipeResponse)
        }
        TestCase.assertEquals(
            listOf<RecipeDto>(),
            recipeResponse.recipes
        )

    }

}

