package com.assignment.codingassignment.network

import retrofit2.Response

sealed class ReceipeState<out T> {
    data class Success<out T>(val data: T) : ReceipeState<T>()
}

fun <T> Response<T>.parseResponse() {
    if (this.isSuccessful && this.body() != null) {
        val responseBody = this.body()
        ReceipeState.Success(responseBody!!)
    }
}