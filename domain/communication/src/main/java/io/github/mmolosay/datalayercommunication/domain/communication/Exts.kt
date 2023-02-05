package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.node.NodeNetworkData

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Iterable<NodeNetworkData>.filterPairedToThis(): List<NodeNetworkData> =
    this.filter { it.isPairedToThisNode }