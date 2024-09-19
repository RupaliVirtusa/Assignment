package com.assignment.codingassignment

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
    }
}