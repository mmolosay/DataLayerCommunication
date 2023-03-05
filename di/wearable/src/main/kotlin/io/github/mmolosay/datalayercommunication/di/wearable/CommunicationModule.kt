package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.com.mmolosay.datalayercommunication.communication.connection.impl.ListenerConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.model.CapabilitySet
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideHandheldConnectionStateProvider(
        capabilityClient: CapabilityClient,
        capabilities: CapabilitySet,
    ): ConnectionStateProvider =
        ListenerConnectionStateProvider(
            capabilityClient = capabilityClient,
            nodeCapability = capabilities.handheld,
        )
}