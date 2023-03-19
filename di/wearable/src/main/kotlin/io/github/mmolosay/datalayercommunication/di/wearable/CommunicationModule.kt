package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.models.CapabilitySet
import io.github.mmolosay.datalayercommunication.data.CapabilityConnectionSource
import io.github.mmolosay.datalayercommunication.data.ConnectionCheckByNodeIdExecutor
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionSource
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeStore
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideConnectionCheckExecutor(
        nodeProvider: NodeProvider,
        nodeStore: NodeStore,
    ): CapabilityConnectionSource.ConnectionCheckExecutor? =
        nodeStore.node?.id?.let { nodeId ->
            ConnectionCheckByNodeIdExecutor(
                nodeProvider = nodeProvider,
                nodeId = nodeId,
            )
        }

    @Provides
    @Singleton
    fun provideHandheldConnectionSource(
        capabilityClient: CapabilityClient,
        capabilities: CapabilitySet,
        nodeStore: NodeStore,
        connectionCheckExecutor: CapabilityConnectionSource.ConnectionCheckExecutor?,
    ): ConnectionSource? {
        connectionCheckExecutor ?: return null
        return nodeStore.node?.id?.let { nodeId ->
            CapabilityConnectionSource(
                capabilityClient = capabilityClient,
                nodeCapability = capabilities.handheld,
                nodeId = nodeId,
                connectionCheckExecutor = connectionCheckExecutor,
            )
        }
    }
}