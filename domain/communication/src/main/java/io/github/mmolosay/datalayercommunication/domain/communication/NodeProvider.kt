package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.Node

/**
 * Exposes an API for obtaining [Node]s in current device network.
 */
interface NodeProvider {
    suspend fun mobile(): Collection<Node>
    suspend fun wearable(): Collection<Node>
}