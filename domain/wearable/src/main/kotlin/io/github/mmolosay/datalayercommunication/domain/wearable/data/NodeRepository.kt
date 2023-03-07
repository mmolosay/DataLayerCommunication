package io.github.mmolosay.datalayercommunication.domain.wearable.data

import io.github.mmolosay.datalayercommunication.communication.model.Node
import io.github.mmolosay.datalayercommunication.utils.resource.Resource

interface NodeRepository {

    suspend fun getConnectedHandheldNode(): Resource<Node> // TODO: extract communication models in individual module

    fun storeNode(node: Node)
}