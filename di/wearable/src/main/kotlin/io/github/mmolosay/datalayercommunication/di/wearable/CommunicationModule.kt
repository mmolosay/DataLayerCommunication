package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.model.CapabilitySet
import io.github.mmolosay.datalayercommunication.data.CapabilityConnectionStateProvider
import io.github.mmolosay.datalayercommunication.data.wearable.HandheldConnectionCheckExecutor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideConnectionCheckExecutor(
        nodeProvider: NodeProvider,
    ): ConnectionCheckExecutor =
        HandheldConnectionCheckExecutor(
            nodeProvider = nodeProvider
        )

    @Provides
    @Singleton
    fun provideHandheldConnectionStateProvider(
        capabilityClient: CapabilityClient,
        capabilities: CapabilitySet,
        connectionCheckExecutor: ConnectionCheckExecutor,
    ): ConnectionStateProvider =
        CapabilityConnectionStateProvider(
            capabilityClient = capabilityClient,
            nodeCapability = capabilities.handheld,
            connectionCheckExecutor = connectionCheckExecutor,
        )
}