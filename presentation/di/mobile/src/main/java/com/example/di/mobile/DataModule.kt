package com.example.di.mobile

import io.github.mmolosay.datalayercommunication.data.handheld.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
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