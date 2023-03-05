package io.github.mmolosay.datalayercommunication.communication.impl

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toNode
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

/**
 * Implementation of [ConnectionStateProvider], powered by Data Layer API and
 * based upon listening for capability changes in current device network.
 */
class CapabilityListenerConnectionStateProvider(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val nodeCapability: Capability,
) : ConnectionStateProvider {

    private val mutableFlow =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val connectionStateFlow: Flow<Boolean> =
        mutableFlow

    private var listener: OnCapabilityChangedListener? = null

    override fun start() {
        if (listener != null) return
        val listener = makeOnCapabilityChangedListener().also { listener = it }
        gmsCapabilityClient.addListener(listener, nodeCapability.value)
    }

    override fun stop() {
        if (listener == null) return
        gmsCapabilityClient.removeListener(requireNotNull(listener))
        this.listener = null
    }

    private fun makeOnCapabilityChangedListener(): OnCapabilityChangedListener =
        OnCapabilityChangedListener { info ->
            if (info.name != nodeCapability.value) return@OnCapabilityChangedListener
            val node = info.nodes
                .map { it.toNode() }
                .find { it.isConnectedToCurrentDevice } // first connected node
            val hasCapableNode = (node != null)
            mutableFlow.tryEmit(hasCapableNode) // should always be successful due to BufferOverflow.DROP_OLDEST
        }
}