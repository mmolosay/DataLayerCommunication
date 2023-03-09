package io.github.mmolosay.datalayercommunication.domain.wearable.usecase

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.firstHandheldNode
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeStore
import io.github.mmolosay.datalayercommunication.utils.resource.getOrNull
import javax.inject.Inject

/**
 * Checks, whether there's __any__ `handheld` device connected to the current one.
 * If there is such, saves it in [nodeStore].
 */
class CheckIsHandheldDeviceConnectedUseCase @Inject constructor(
    private val nodeProvider: NodeProvider,
    private val nodeStore: NodeStore,
) {

    suspend operator fun invoke(): Boolean {
        val node = nodeProvider.firstHandheldNode()
            .getOrNull()
            ?.also { nodeStore.node = it }
        return (node != null)
    }
}