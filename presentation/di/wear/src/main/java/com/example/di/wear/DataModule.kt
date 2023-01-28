package com.example.di.wear

import io.github.mmolosay.datalayercommunication.data.wearable.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
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