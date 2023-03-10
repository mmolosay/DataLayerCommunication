package io.github.mmolosay.datalayercommunication.communication.models

/**
 * Destination of a data to be sent to.
 */
data class Destination(
    val nodeId: String,
    val path: Path,
)