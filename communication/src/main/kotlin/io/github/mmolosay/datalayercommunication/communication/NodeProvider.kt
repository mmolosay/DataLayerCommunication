package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.model.Node

/**
 * Exposes an API for obtaining nodes in current device network.
 */
interface NodeProvider {

    /**
     * Collects data about all reachable `handheld` nodes in current device network.
     * This `suspend` function will switch coroutine dispatcher to an appropriate one.
     */
    suspend fun handheld(): Collection<Node>

    /**
     * Collects data about all reachable `wearable` nodes in current device network.
     * This `suspend` function will switch coroutine dispatcher to an appropriate one.
     */
    suspend fun wearable(): Collection<Node>
}