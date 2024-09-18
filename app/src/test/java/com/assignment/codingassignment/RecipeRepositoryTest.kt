package com.assignment.codingassignment

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
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
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@RunWith(AndroidJUnit4::class)
class RecipeRepositoryTest {
    @Mock
    lateinit var recipeTestService: RecipeTestService

    lateinit var context: Context


    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
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

