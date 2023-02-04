package io.github.mmolosay.datalayercommunication.domain.communication.model.node

import io.github.mmolosay.datalayercommunication.domain.communication.model.Destination
import io.github.mmolosay.datalayercommunication.domain.communication.model.Path

/**
 * Represents a concrete device (node) in current device network.
 */
data class Node(
    val id: String,
    val name: String,
)

/**
 * Assembles new [Destination], using receiver [Node].
 */
fun Node.toDestination(path: Path): Destination =
    Destination(
        nodeId = id,
        path = path,
    )