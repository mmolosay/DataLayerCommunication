package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode

/**
 * Implementation of [ConnectionCheckExecutor], that checks, whether there's a connection between
 * current device and __any__ handheld one.
 */
class HandheldConnectionCheckExecutor(
    private val nodeProvider: NodeProvider,
) : ConnectionCheckExecutor {

    override suspend fun areDevicesConnected(): Boolean =
        nodeProvider.singleConnectedHandheldNode().isSuccess
}