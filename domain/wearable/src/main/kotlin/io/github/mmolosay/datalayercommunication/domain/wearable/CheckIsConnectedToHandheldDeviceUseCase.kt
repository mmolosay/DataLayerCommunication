package io.github.mmolosay.datalayercommunication.domain.wearable

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.model.Node
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode
import javax.inject.Inject

class CheckIsConnectedToHandheldDeviceUseCase @Inject constructor(
    private val nodeProvider: NodeProvider,
) {

    suspend operator fun invoke(): Boolean =
        (getConnectedNode() != null)

    private suspend fun getConnectedNode(): Node? =
        nodeProvider
            .singleConnectedHandheldNode()
            .getOrNull()
}