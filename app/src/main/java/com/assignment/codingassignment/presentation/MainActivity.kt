package com.assignment.codingassignment.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.assignment.codingassignment.R
import com.assignment.codingassignment.databinding.ActivityMainBinding
import com.assignment.codingassignment.network.RecipeListState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: RecipeListViewModel by viewModels()
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val recipeAdapter = RecipeAdapter(emptyList())
        binding.rvReceipe.apply {
            layoutManager = context?.let { LinearLayoutManager(it) }
            setHasFixedSize(true)
            adapter = recipeAdapter
        }
        viewModel.alRecipeList.observe(this) { recipeState ->
            when (recipeState) {
                is RecipeListState.Loading -> {
                    binding.progressCircular.visibility = View.VISIBLE
                }

                is RecipeListState.RecipeListLoaded -> {
                    val alRecipes = recipeState.response.recipes
                    binding.tvRecipeCount.text = "Recipe Count : " + recipeState.response.count
                    binding.rvReceipe.visibility = View.VISIBLE
                    binding.progressCircular.visibility = View.GONE
                    recipeAdapter.updateReceipeList(alRecipes)
                }

                is RecipeListState.Error -> {
                    binding.rvReceipe.visibility = View.GONE
                    binding.progressCircular.visibility = View.GONE
                }
            }
        }
    }
}
