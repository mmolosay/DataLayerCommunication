package com.example.di.mobile

import com.example.data.mobile.AnimalsRepositoryImpl
import com.example.domain.AnimalsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAnimalsRepository(): AnimalsRepository =
        AnimalsRepositoryImpl()
}