package io.github.mmolosay.datalayercommunication.communication.model

/**
 * Represents a concrete device, which is __connected to the
 * current one__ (on which application is presently runnging).
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