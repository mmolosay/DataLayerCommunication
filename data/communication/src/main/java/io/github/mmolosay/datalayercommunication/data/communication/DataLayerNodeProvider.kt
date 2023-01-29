package io.github.mmolosay.datalayercommunication.data.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.Node
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import com.google.android.gms.wearable.CapabilityClient
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val capabilityClient: CapabilityClient,
    private val handheldCapability: String,
    private val wearableCapability: String,
) : NodeProvider {

    override suspend fun handheld(): Collection<Node> =
        getNodesWithCapability(handheldCapability)

    override suspend fun wearable(): Collection<Node> =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: String): Collection<Node> =
        capabilityClient
            .getCapability(capability, CapabilityClient.FILTER_REACHABLE)
            .await()
            .nodes
            .map { Node(id = it.id, isNearby = it.isNearby) }
}