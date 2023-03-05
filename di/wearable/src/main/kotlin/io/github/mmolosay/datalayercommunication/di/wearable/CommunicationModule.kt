package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProviderFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideHandheldConnectionStateProvider(
        factory: ConnectionStateProviderFactory,
    ): ConnectionStateProvider =
        factory.createForHandheld()
}