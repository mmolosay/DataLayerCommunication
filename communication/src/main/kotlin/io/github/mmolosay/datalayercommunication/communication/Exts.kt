package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.model.Node

/**
 * Returns a filtered collection with only nodes connected to current device.
 */
fun Collection<Node>.filterConnectedToCurrentDevice(): Collection<Node> =
    this.filter { it.isConnectedToCurrentDevice }

/**
 * Returns [Result] with [first] `handheld` node, which is connected to current device.
 */
suspend fun NodeProvider.connectedHandheldNode(): Result<Node> = // TODO: return resource
    runCatching {
        this
            .handheld()
            .filterConnectedToCurrentDevice()
            .first()
    }
