package com.example.data.communication

import com.example.domain.communication.model.Node
import com.example.domain.communication.NodeProvider
import com.google.android.gms.wearable.CapabilityClient
import kotlinx.coroutines.tasks.await

/**
 * [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val capabilityClient: CapabilityClient,
    private val mobileCapability: String,
    private val wearableCapability: String,
) : NodeProvider {

    override suspend fun mobile(): Collection<Node> =
        getNodesWithCapability(mobileCapability)

    override suspend fun wearable(): Collection<Node> =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: String): Collection<Node> =
        capabilityClient
            .getCapability(capability, CapabilityClient.FILTER_REACHABLE)
            .await()
            .nodes
            .map { Node(id = it.id, isNearby = it.isNearby) }
}