package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.models.CapabilitySet
import io.github.mmolosay.datalayercommunication.data.CapabilityConnectionFlowProvider
import io.github.mmolosay.datalayercommunication.data.ConnectionCheckByNodeIdExecutor
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionFlowProvider
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
    ): CapabilityConnectionFlowProvider.ConnectionCheckExecutor? =
        nodeStore.node?.id?.let { nodeId ->
            ConnectionCheckByNodeIdExecutor(
                nodeProvider = nodeProvider,
                nodeId = nodeId,
            )
        }

    @Provides
    @Singleton
    fun provideHandheldConnectionFlowProvider(
        capabilityClient: CapabilityClient,
        capabilities: CapabilitySet,
        nodeStore: NodeStore,
        connectionCheckExecutor: CapabilityConnectionFlowProvider.ConnectionCheckExecutor?,
    ): ConnectionFlowProvider? {
        connectionCheckExecutor ?: return null
        return nodeStore.node?.id?.let { nodeId ->
            CapabilityConnectionFlowProvider(
                capabilityClient = capabilityClient,
                nodeCapability = capabilities.handheld,
                nodeId = nodeId,
                connectionCheckExecutor = connectionCheckExecutor,
            )
        }
    }
}