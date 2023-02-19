package io.github.mmolosay.datalayercommunication.communication.model

/**
 * Represents a concrete device in current device network in respect of a node, on which
 * application is currently running.
 */
data class Node(
    val id: String,
    val name: String,
    val isPairedToThisNode: Boolean,
)

/**
 * Assembles new [Destination], using receiver [Node].
 */
fun Node.toDestination(path: Path): Destination =
    Destination(
        nodeId = id,
        path = path,
    )