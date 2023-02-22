package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.model.Node

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Collection<Node>.filterPairedToThis(): Collection<Node> =
    this.filter { it.isConnectedToCurrentDevice }

/**
 * Returns [Result] with [single] `handheld` node, which is paired to current device.
 */
suspend fun NodeProvider.singlePairedHandheldNode(): Result<Node> =
    runCatching {
        this
            .handheld()
            .filterPairedToThis()
            .single()
    }
