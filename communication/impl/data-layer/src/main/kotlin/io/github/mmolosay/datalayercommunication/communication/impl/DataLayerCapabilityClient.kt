package io.github.mmolosay.datalayercommunication.communication.impl

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toDataLayer
import io.github.mmolosay.datalayercommunication.communication.models.Capability
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

class DataLayerCapabilityClient(
    private val gmsCapabilityClient: GmsCapabilityClient,
) : CapabilityClient {

    private val listeners = mutableMapOf<OnCapabilityChangedCallback, OnCapabilityChangedListener>()

    override fun OnCapabilityChangedCallback.addFor(capability: Capability) {
        gmsCapabilityClient.addListener(this.toDataLayer(), capability.toDataLayer())
    }

    override fun OnCapabilityChangedCallback.remove() {
        val listener = listeners.remove(this) ?: return
        gmsCapabilityClient.removeListener(listener)
    }
}