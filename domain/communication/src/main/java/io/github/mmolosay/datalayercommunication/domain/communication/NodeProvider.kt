package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.node.NodeNetworkData

/**
 * Exposes an API for obtaining nodes in current device network.
 */
interface NodeProvider {

    /**
     * Collects data about all reachable `handheld` nodes in current device network.
     */
    suspend fun handheld(): Collection<NodeNetworkData>

    /**
     * Collects data about all reachable `wearable` nodes in current device network.
     */
    suspend fun wearable(): Collection<NodeNetworkData>
}