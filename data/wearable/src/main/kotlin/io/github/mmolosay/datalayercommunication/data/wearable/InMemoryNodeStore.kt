package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.models.Node
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeStore

class InMemoryNodeStore : NodeStore {
    override var node: Node? = null
}