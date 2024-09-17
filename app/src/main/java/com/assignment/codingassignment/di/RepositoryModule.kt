package com.assignment.codingassignment.di

import com.assignment.codingassignment.network.RecipeService
import com.assignment.codingassignment.repository.RecipeRepository
import com.assignment.codingassignment.repository.RecipeRepository_Impl
import com.assignment.codingassignment.network.model.RecipeDtoMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(
        recipeService: RecipeService,
        recipeDtoMapper: RecipeDtoMapper
    ): RecipeRepository {
        return RecipeRepository_Impl(recipeService, recipeDtoMapper)
    }
}