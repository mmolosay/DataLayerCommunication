package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.CommunicationFailures.NoSuchNodeFailure
import io.github.mmolosay.datalayercommunication.domain.communication.model.Node
import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import io.github.mmolosay.datalayercommunication.domain.resource.success

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Iterable<Node>.filterPairedToThis(): List<Node> =
    this.filter { it.isPairedToThisNode }

/**
 * Returns [Resource] with single node data, or [NoSuchNodeFailure].
 */
fun Iterable<Node>.resourceSingle(): Resource<Node> =
    this
        .singleOrNull()
        ?.let { Resource.success(it) }
        ?: NoSuchNodeFailure
