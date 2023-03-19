package io.github.mmolosay.datalayercommunication.data

import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.communication.models.Capability
import io.github.mmolosay.datalayercommunication.domain.data.ConnectionSource
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged

/**
 * Implementation of [ConnectionSource], based on listening for
 * capability changes in current device network.
 */
class CapabilityConnectionSource(
    private val capabilityClient: CapabilityClient,
    private val nodeCapability: Capability,
    private val nodeId: String,
    private val connectionCheckExecutor: ConnectionCheckExecutor,
) : ConnectionSource {

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

    /**
     * Executes a one-shot connection check between current node and one, specified by implementation.
     */
    interface ConnectionCheckExecutor {

        /**
         * @return whether nodese are connected or not.
         */
        suspend fun areNodesConnected(): Boolean
    }
}