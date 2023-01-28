package io.github.mmolosay.datalayercommunication.domain.communication.model

/**
 * Represents a concrete device (node) in current device network.
 */
data class Node(
    val id: String,
    val isNearby: Boolean,
)

/**
 * Assembles new [Destination], using receiver [Node].
 */
fun Node.toDestination(path: String): Destination =
    Destination(
        nodeId = id,
        path = path,
    )