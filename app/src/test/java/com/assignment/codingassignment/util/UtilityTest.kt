package com.assignment.codingassignment.util

import android.content.Context
import java.io.IOException

class UtilityTest {
    companion object{
        fun loadJSONFromAsset(context: Context, fileName: String): String? {
            return try {
                context.assets.open(fileName).bufferedReader().use { it.readText() }
            } catch (ex: IOException) {
                ex.printStackTrace()
                null
            }
        }
    }

}