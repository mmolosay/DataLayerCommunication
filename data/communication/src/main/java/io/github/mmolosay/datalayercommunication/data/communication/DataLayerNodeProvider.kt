package io.github.mmolosay.datalayercommunication.data.communication

import com.google.android.gms.wearable.CapabilityClient
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.model.Capability
import io.github.mmolosay.datalayercommunication.domain.communication.model.Node
import kotlinx.coroutines.tasks.await

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
            .map { Node(id = it.id, isNearby = it.isNearby) }
}