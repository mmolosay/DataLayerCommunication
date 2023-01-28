package com.example.di.wear

import com.example.data.wear.AnimalsRepositoryImpl
import com.example.domain.AnimalsRepository
import com.example.domain.communication.CommunicationClient
import com.example.domain.communication.NodeProvider
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
    fun provideAnimalsRepository(
        nodeProvider: NodeProvider,
        communicationClient: CommunicationClient,
    ): AnimalsRepository =
        AnimalsRepositoryImpl(
            nodeProvider = nodeProvider,
            communicationClient = communicationClient,
        )
}