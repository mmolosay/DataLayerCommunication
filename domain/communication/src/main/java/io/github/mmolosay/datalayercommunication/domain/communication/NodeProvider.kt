package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.Node

/**
 * Exposes an API for obtaining [Node]s in current device network.
 */
interface NodeProvider {

    /**
     * Collects all reachable `handheld` nodes.
     */
    suspend fun handheld(): Collection<Node>

    /**
     * Collects all reachable `wearable` nodes.
     */
    suspend fun wearable(): Collection<Node>
}