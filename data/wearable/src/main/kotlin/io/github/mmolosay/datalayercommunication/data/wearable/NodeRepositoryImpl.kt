package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.communication.connectedHandheldNode
import io.github.mmolosay.datalayercommunication.communication.model.Node
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success

class NodeRepositoryImpl(
    private val nodeProvider: NodeProvider,
) : NodeRepository {

    private var handheldNode: Node? = null // in-memory store

    override suspend fun getConnectedHandheldNode(): Resource<Node> =
        nodeProvider
            .connectedHandheldNode()
            .getOrNull()
            ?.let { Resource.success(it) }
            ?: ConnectionFailure

    override fun storeNode(node: Node) {
        handheldNode = node
    }
}