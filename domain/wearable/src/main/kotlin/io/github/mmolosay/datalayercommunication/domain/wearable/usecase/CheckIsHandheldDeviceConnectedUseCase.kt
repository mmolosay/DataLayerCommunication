package io.github.mmolosay.datalayercommunication.domain.wearable.usecase

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.firstHandheldNode
import io.github.mmolosay.datalayercommunication.communication.models.Node
import io.github.mmolosay.datalayercommunication.utils.resource.getOrNull

/**
 * Checks, whether there's __any__ `handheld` device connected to the current one.
 * If there is such, executes specific [onHandheldDeviceConnected] callback.
 */
class CheckIsHandheldDeviceConnectedUseCase(
    private val nodeProvider: NodeProvider,
    private val onHandheldDeviceConnected: OnHandheldDeviceConnected,
) {

    suspend operator fun invoke(): Boolean {
        val node = nodeProvider.firstHandheldNode()
            .getOrNull()
            ?.also { onHandheldDeviceConnected(it) }
        return (node != null)
    }

    interface OnHandheldDeviceConnected : (Node) -> Unit
}