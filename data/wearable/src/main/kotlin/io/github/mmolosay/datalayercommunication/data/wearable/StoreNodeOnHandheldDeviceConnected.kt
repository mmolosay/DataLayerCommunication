package io.github.mmolosay.datalayercommunication.data.wearable

import io.github.mmolosay.datalayercommunication.communication.models.Node
import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeStore
import io.github.mmolosay.datalayercommunication.domain.wearable.usecase.CheckIsHandheldDeviceConnectedUseCase.OnHandheldDeviceConnected

/**
 * Implementation of [OnHandheldDeviceConnected], that stores ID of specified handheld node.
 */
class StoreNodeOnHandheldDeviceConnected(
    private val nodeStore: NodeStore,
) : OnHandheldDeviceConnected {

    private var hadBeenUsed = false

    override fun invoke(handheldNode: Node) {
        if (hadBeenUsed) error("This StoreIdOnHandheldDeviceConnected had been already used") // fool proof
        nodeStore.node = handheldNode
        hadBeenUsed = true
    }
}