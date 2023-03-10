package io.github.mmolosay.datalayercommunication.data

import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionFlowProvider
import io.github.mmolosay.datalayercommunication.communication.models.Capability
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Implementation of [ConnectionFlowProvider], based on listening for
 * capability changes in current device network.
 */
class CapabilityConnectionFlowProvider(
    private val capabilityClient: CapabilityClient,
    private val nodeCapability: Capability,
    private val nodeId: String,
    private val connectionCheckExecutor: ConnectionCheckExecutor,
) : ConnectionFlowProvider {

    override val connectionFlow: Flow<Boolean> =
        callbackFlow {
            val initialState = connectionCheckExecutor.areNodesConnected()
            send(initialState)

            val callback = makeOnCapabilityChangedCallback()
            with(capabilityClient) { callback.addFor(nodeCapability) }

            awaitClose {
                with(capabilityClient) { callback.remove() }
            }
        }.distinctUntilChanged()

    private fun ProducerScope<Boolean>.makeOnCapabilityChangedCallback(): OnCapabilityChangedCallback =
        OnCapabilityChangedCallback callback@{ capability, nodes ->
            if (capability != nodeCapability) return@callback
            val hasNodeWithRequiredId = nodes.any { it.id == nodeId }
            trySend(hasNodeWithRequiredId)
        }
}