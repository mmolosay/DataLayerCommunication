package io.github.mmolosay.datalayercommunication.domain.communication.model.node

/**
 * Data about specified [node] in terms of its device network.
 */
data class NodeNetworkData(
    val node: Node,
    val isPairedToThisNode: Boolean,
)