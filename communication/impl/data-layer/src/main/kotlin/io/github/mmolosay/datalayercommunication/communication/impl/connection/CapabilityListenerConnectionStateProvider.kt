package io.github.mmolosay.datalayercommunication.communication.impl.connection

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toNode
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.channels.ProducerScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

/**
 * Implementation of [ConnectionStateProvider], powered by Data Layer API and
 * based upon listening for capability changes in current device network.
 */
class CapabilityListenerConnectionStateProvider(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val nodeCapability: Capability,
    private val connectionCheckExecutor: ConnectionCheckExecutor,
) : ConnectionStateProvider {

    override val connectionStateFlow: Flow<Boolean> =
        callbackFlow {
            val listener = createAndRegisterListener()
            val initialState = connectionCheckExecutor.checkConnectionState()
            send(initialState)
            awaitClose {
                unregisterListener(listener)
            }
        }

    private fun ProducerScope<Boolean>.createAndRegisterListener(): OnCapabilityChangedListener =
        makeOnCapabilityChangedListener()
            .also {
                gmsCapabilityClient.addListener(it, nodeCapability.value)
            }

    private fun unregisterListener(listener: OnCapabilityChangedListener) {
        gmsCapabilityClient.removeListener(listener)
    }

    private fun ProducerScope<Boolean>.makeOnCapabilityChangedListener(): OnCapabilityChangedListener =
        OnCapabilityChangedListener { info ->
            if (info.name != nodeCapability.value) return@OnCapabilityChangedListener
            val node = info.nodes
                .map { it.toNode() }
                .find { it.isConnectedToCurrentDevice } // first connected node
            val hasCapableNode = (node != null)
            trySend(hasCapableNode)
        }
}