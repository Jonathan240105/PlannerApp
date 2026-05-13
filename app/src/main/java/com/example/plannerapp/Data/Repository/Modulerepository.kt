package com.example.plannerapp.Data.Repository

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class Modulerepository {
    @Binds
    @Singleton
    abstract fun provideRepository(
        repositoryImp: RepositoryImp
    ): Repository
}