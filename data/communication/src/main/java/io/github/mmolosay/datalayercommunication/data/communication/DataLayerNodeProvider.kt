package io.github.mmolosay.datalayercommunication.data.communication

import com.google.android.gms.wearable.CapabilityClient
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.model.Capability
import io.github.mmolosay.datalayercommunication.domain.communication.model.Node
import kotlinx.coroutines.tasks.await
import com.google.android.gms.wearable.Node as DataLayerNode

/**
 * Implementation of [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val capabilityClient: CapabilityClient,
    private val handheldCapability: Capability,
    private val wearableCapability: Capability,
) : NodeProvider {

    override suspend fun handheld(): Collection<Node> =
        getNodesWithCapability(handheldCapability)

    override suspend fun wearable(): Collection<Node> =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: Capability): Collection<Node> =
        capabilityClient
            .getCapability(capability.value, CapabilityClient.FILTER_REACHABLE)
            .await()
            .nodes
            .map { it.toNodeNetworkData() }

    private fun DataLayerNode.toNodeNetworkData(): Node =
        Node(
            id = this.id,
            name = this.displayName,
            isPairedToThisNode = this.isNearby,
        )
}