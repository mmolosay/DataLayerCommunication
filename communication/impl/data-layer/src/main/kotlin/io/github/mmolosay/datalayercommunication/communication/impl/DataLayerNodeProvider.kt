package io.github.mmolosay.datalayercommunication.communication.impl

import com.google.android.gms.wearable.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.model.Node
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds
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

    private suspend fun getNodesWithCapability(capability: Capability): Collection<Node> {
        val capabilityInfo = withTimeoutOrNull(3.seconds) {
            capabilityClient
                .getCapability(capability.value, CapabilityClient.FILTER_REACHABLE)
                .await()
        }
        return capabilityInfo
            ?.nodes
            ?.map { it.toNode() }
            ?: return emptyList()
    }

    private fun DataLayerNode.toNode(): Node =
        Node(
            id = this.id,
            name = this.displayName,
            isConnectedToCurrentDevice = this.isNearby,
        )
}