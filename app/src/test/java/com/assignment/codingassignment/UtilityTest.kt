package com.assignment.codingassignment

import com.assignment.codingassignment.network.responses.RecipeSearchResponse
import com.google.gson.Gson
import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source

class UtilityTest {
    companion object {
        fun enqueueMockResponse(fileName: String): MockResponse {
            val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()

            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            return mockResponse
        }

        fun enqueueRecipeMockResponse(fileName: String): RecipeSearchResponse {
            val jsonString = javaClass.classLoader!!.getResourceAsStream(fileName)
                .bufferedReader().use { it.readText() }
            val gson = Gson()
            val recipeSearchResponse =gson.fromJson(jsonString, RecipeSearchResponse::class.java)
            return recipeSearchResponse

        }
    }
}