package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.model.Node

/**
 * Exposes an API for obtaining nodes in current device network.
 */
interface NodeProvider {

    /**
     * Collects data about all reachable `handheld` nodes in current device network.
     */
    suspend fun handheld(): Collection<Node>

    /**
     * Collects data about all reachable `wearable` nodes in current device network.
     */
    suspend fun wearable(): Collection<Node>
}