package com.assignment.codingassignment.api

import com.assignment.codingassignment.BuildConfig
import com.assignment.codingassignment.UtilityTest.Companion.enqueueMockResponse
import com.assignment.codingassignment.network.RecipeService
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecipeApiTest {
    private lateinit var service: RecipeService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RecipeService::class.java)
    }


    @Test
    fun getSearch_sentRequest_receivedExpected() {

        runBlocking {
            enqueueMockResponse("recipe.json")
            val responseBody =
                service.search(BuildConfig.APP_TOKEN, 3, "beef carrot potato onion").body()
            val request = server.takeRequest()
            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("search/?page=3&query=beef%20carrot%20potato%20onion")
        }
    }

    @Test
    fun getSearch_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("recipe.json")
            val responseBody =
                service.search(BuildConfig.APP_TOKEN, 3, "beef carrot potato onion").body()
            val recipesList = responseBody!!.recipes
            assertThat(recipesList.size).isEqualTo(118)
        }
    }

    @Test
    fun getSearch_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("recipe.json")
            val responseBody =
                service.search(BuildConfig.APP_TOKEN, 3, "beef carrot potato onion").body()
            val recipesList = responseBody!!.recipes
            val recipe = recipesList[0]
            assertThat(recipe.title).isEqualTo("Pizza Potato Skins")
            assertThat(recipe.description).isEqualTo("N/A")
            assertThat(recipe.sourceUrl).isEqualTo("http://thepioneerwoman.com/cooking/2013/04/pizza-potato-skins/")
            assertThat(recipe.rating).isEqualTo("16")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }
}