package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.model.PathSet
import io.github.mmolosay.datalayercommunication.data.wearable.AnimalsRepositoryImpl
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    @Singleton
    fun provideAnimalsRepository(
        nodeProvider: NodeProvider,
        communicationClient: CommunicationClient,
        paths: PathSet,
    ): AnimalsRepository =
        AnimalsRepositoryImpl(
            nodeProvider = nodeProvider,
            communicationClient = communicationClient,
            getAllAnimalsPath = paths.getAllAnimals,
            deleteAnimalByIdPath = paths.deleteRandomAnimalById,
        )
}