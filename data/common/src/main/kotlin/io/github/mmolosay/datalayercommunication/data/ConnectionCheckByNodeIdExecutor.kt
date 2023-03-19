package io.github.mmolosay.datalayercommunication.data

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.data.CapabilityConnectionFlowProvider.ConnectionCheckExecutor

/**
 * Implementation of [CapabilityConnectionFlowProvider.ConnectionCheckExecutor], that checks,
 * whether there's a connection between current device and one with specified [nodeId].
 */
class ConnectionCheckByNodeIdExecutor(
    private val nodeProvider: NodeProvider,
    private val nodeId: String,
) : ConnectionCheckExecutor {

    override suspend fun areNodesConnected(): Boolean =
        nodeProvider
            .all()
            ?.any { it.id == nodeId }
            ?: false
}