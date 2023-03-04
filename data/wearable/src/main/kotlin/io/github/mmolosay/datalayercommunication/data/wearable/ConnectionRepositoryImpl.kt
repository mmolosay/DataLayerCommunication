package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.model.Node
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode
import io.github.mmolosay.datalayercommunication.domain.wearable.repository.ConnectionRepository

class ConnectionRepositoryImpl(
    private val nodeProvider: NodeProvider,
) : ConnectionRepository {

    override suspend fun isConnectedToHandheldDevice(): Boolean =
        (getConnectedHandheldNode() != null)

    private suspend fun getConnectedHandheldNode(): Node? =
        nodeProvider
            .singleConnectedHandheldNode()
            .getOrNull()
}