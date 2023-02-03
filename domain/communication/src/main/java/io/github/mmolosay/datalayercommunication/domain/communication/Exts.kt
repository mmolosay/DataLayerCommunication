package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.node.NodeNetworkData

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Collection<NodeNetworkData>.pairedToThis(): Collection<NodeNetworkData> =
    this.filter { it.isPairedToThisNode }