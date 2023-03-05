package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toDataLayerListener
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.CoroutineScope
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener as DataLayerOnCapabilityChangedListener

class DataLayerCapabilityClient(
    private val gmsCapabilityClient: GmsCapabilityClient,
) : CapabilityClient {

    private val listeners =
        mutableMapOf<OnCapabilityChangedListener, DataLayerOnCapabilityChangedListener>()

    override fun addListener(
        coroutineScope: CoroutineScope,
        listener: OnCapabilityChangedListener,
        capability: Capability,
    ) {
        val dataLayerListener = listener.toDataLayerListener(coroutineScope)
        val dataLayerCapability = capability.value
        gmsCapabilityClient.addListener(dataLayerListener, dataLayerCapability)
        listeners[listener] = dataLayerListener
    }

    override fun removeListener(listener: OnCapabilityChangedListener) {
        val dataLayerListener = listeners.remove(listener) ?: return
        gmsCapabilityClient.removeListener(dataLayerListener)
    }
}