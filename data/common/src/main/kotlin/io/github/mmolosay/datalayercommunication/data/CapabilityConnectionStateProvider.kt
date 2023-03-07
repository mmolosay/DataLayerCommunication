package io.github.mmolosay.datalayercommunication.data

import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

/**
 * Implementation of [ConnectionStateProvider], based on listening for
 * capability changes in current device network.
 */
class CapabilityConnectionStateProvider(
    private val capabilityClient: CapabilityClient,
    private val nodeCapability: Capability,
    private val connectionCheckExecutor: ConnectionCheckExecutor,
) : ConnectionStateProvider {

    override val connectionStateFlow: Flow<Boolean> =
        callbackFlow {
            val initialState = connectionCheckExecutor.areDevicesConnected()
            send(initialState)

            val callback = makeOnCapabilityChangedCallback()
            with(capabilityClient) { callback.addFor(nodeCapability) }

            awaitClose {
                with(capabilityClient) { callback.remove() }
            }
        }

    private fun ProducerScope<Boolean>.makeOnCapabilityChangedCallback(): OnCapabilityChangedCallback =
        OnCapabilityChangedCallback callback@{ capability, nodes ->
            if (capability != nodeCapability) return@callback
            val firstConnectedNode = nodes.find { it.isConnectedToCurrentDevice }
            val hasCapableNode = (firstConnectedNode != null)
            trySend(hasCapableNode)
        }
}