package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.failures.ConnectionFailure
import io.github.mmolosay.datalayercommunication.communication.failures.NoSuchNodeFailure
import io.github.mmolosay.datalayercommunication.communication.models.Node
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success

/**
 * Returns [Resource] with [first] `handheld` node, which is connected to current device.
 *
 * @return first `handheld` node, or:
 *  - [ConnectionFailure], if nodes cannot be obtained
 *  - [NoSuchNodeFailure], if there is no such.
 */
suspend fun NodeProvider.firstHandheldNode(): Resource<Node> {
    val nodes = this.handheld() ?: return ConnectionFailure // but actually a "timeout failure"
    val node = nodes.firstOrNull() ?: return NoSuchNodeFailure
    return Resource.success(node)
}
