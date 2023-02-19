package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.communication.model.Node

/**
 * Returns a filtered collection with only nodes paired to current device.
 */
fun Collection<Node>.filterPairedToThis(): Collection<Node> =
    this.filter { it.isPairedToThisNode }

suspend fun NodeProvider.singlePairedHandheldNode(): Result<Node> =
    runCatching {
        this
            .handheld()
            .filterPairedToThis()
            .single()
    }
