package io.github.mmolosay.datalayercommunication.domain.wearable.data

import io.github.mmolosay.datalayercommunication.communication.models.Node

/**
 * Stores __presently connected__ to the current device [Node].
 */
interface NodeStore {
    var node: Node?
}