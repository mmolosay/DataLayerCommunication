package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.model.Node

/**
 * Exposes an API for learning [Capability]s of nodes in current device network.
 */
interface CapabilityClient {

    fun OnCapabilityChangedCallback.addFor(capability: Capability)
    fun OnCapabilityChangedCallback.remove()

    /**
     * Callback to be invoked, when nodes with some [Capability] enter or leave current device network.
     */
    fun interface OnCapabilityChangedCallback {

        /**
         * Called for some [capability] and [nodes], possessing it.
         */
        fun onCapabilityChanged(capability: Capability, nodes: Collection<Node>)
    }
}