package io.github.mmolosay.datalayercommunication.communication.client

import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.model.Node
import kotlinx.coroutines.CoroutineScope

interface CapabilityClient {

    fun addListener(
        coroutineScope: CoroutineScope,
        listener: OnCapabilityChangedListener,
        capability: Capability,
    )

    fun removeListener(listener: OnCapabilityChangedListener)

    fun interface OnCapabilityChangedListener {
        suspend fun onCapabilityChanged(capability: Capability, nodes: Collection<Node>)
    }
}