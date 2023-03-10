package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.data.wearable.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.data.wearable.InMemoryNodeStore
import io.github.mmolosay.datalayercommunication.domain.data.AnimalsRepository
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAnimalsRepository(
        nodeProvider: NodeProvider,
        communicationClient: CommunicationClient,
        paths: io.github.mmolosay.datalayercommunication.communication.models.PathSet,
    ): AnimalsRepository =
        AnimalsRepositoryImpl(
            nodeProvider = nodeProvider,
            communicationClient = communicationClient,
            requestsPath = paths.requests,
        )

    @Provides
    @Singleton
    fun provideConnectedNodeStore(): NodeStore =
        InMemoryNodeStore()
}