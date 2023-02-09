package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.CommunicationFailures.NoSuchNodeFailure
import io.github.mmolosay.datalayercommunication.domain.communication.model.node.NodeNetworkData
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Iterable<NodeNetworkData>.filterPairedToThis(): List<NodeNetworkData> =
    this.filter { it.isPairedToThisNode }

/**
 * Returns [Resource] with single node data, or [NoSuchNodeFailure].
 */
fun Iterable<NodeNetworkData>.resourceSingle(): Resource<NodeNetworkData> =
    this
        .singleOrNull()
        ?.let { Resource.success(it) }
        ?: NoSuchNodeFailure
