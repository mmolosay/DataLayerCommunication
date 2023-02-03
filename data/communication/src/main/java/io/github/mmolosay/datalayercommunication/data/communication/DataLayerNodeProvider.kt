package io.github.mmolosay.datalayercommunication.data.communication

import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.Node as DataLayerNode
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.model.Capability
import io.github.mmolosay.datalayercommunication.domain.communication.model.node.Node
import io.github.mmolosay.datalayercommunication.domain.communication.model.node.NodeNetworkData
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val capabilityClient: CapabilityClient,
    private val handheldCapability: Capability,
    private val wearableCapability: Capability,
) : NodeProvider {

    override suspend fun handheld(): Collection<NodeNetworkData> =
        getNodesWithCapability(handheldCapability)

    override suspend fun wearable(): Collection<NodeNetworkData> =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: Capability): Collection<NodeNetworkData> =
        capabilityClient
            .getCapability(capability.value, CapabilityClient.FILTER_REACHABLE)
            .await()
            .nodes
            .map { it.toNodeNetworkData() }

    private fun DataLayerNode.toNodeNetworkData(): NodeNetworkData =
        NodeNetworkData(
            node = Node(id = this.id),
            isPairedToThisNode = this.isNearby,
        )
}