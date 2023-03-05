package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

/**
 * Implementation of [ConnectionStateProvider], powered by Data Layer API and
 * based upon listening for capability changes in current device network.
 */
class CapabilityListenerConnectionStateProvider(
    private val capabilityClient: CapabilityClient,
    private val nodeCapability: Capability,
) : ConnectionStateProvider {

    private val mutableFlow =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)

    override val connectionStateFlow: Flow<Boolean> =
        mutableFlow

    private var job: Job? = null
    private var listener: OnCapabilityChangedListener? = null

    override fun start(coroutineScope: CoroutineScope): Job {
        job?.let { if (it.isActive) return it }
        return coroutineScope.launch(context = Job()) {
            val listener = makeOnCapabilityChangedListener().also { listener = it }
            capabilityClient.addListener(coroutineScope, listener, nodeCapability)
        }.also { job = it }
    }

    override fun stop() {
        job?.cancel()
        capabilityClient.removeListener(requireNotNull(listener))
        this.job = null
        this.listener = null
    }

    private fun makeOnCapabilityChangedListener() =
        OnCapabilityChangedListener l@{ capability, nodes ->
            if (capability != nodeCapability) return@l
            val node = nodes.find { it.isConnectedToCurrentDevice } // first connected node
            val hasCapableNode = (node != null)
            mutableFlow.emit(hasCapableNode)
        }
}