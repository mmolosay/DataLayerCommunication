package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.models.Node

/**
 * Exposes an API for obtaining nodes from current device network.
 * All rentruned nodes are connected to the current device, as definition of [Node] states.
 *
 * All `suspend` methods will switch current coroutine dispatcher to an appropriate one.
 */
interface NodeProvider {

    /**
     * Collects all [Node]s in current device network.
     *
     * @return collection of nodes, or `null`, if process of collecting had failed / timed out.
     */
    suspend fun all(): Collection<Node>?

    /**
     * Collects __handheld__ nodes in current device network.
     *
     * @return collection of nodes, or `null`, if process of collecting had failed / timed out.
     */
    suspend fun handheld(): Collection<Node>?

    /**
     * Collects _wearable_ nodes in current device network.
     *
     * @return collection of nodes, or `null`, if process of collecting had failed / timed out.
     */
    suspend fun wearable(): Collection<Node>?
}