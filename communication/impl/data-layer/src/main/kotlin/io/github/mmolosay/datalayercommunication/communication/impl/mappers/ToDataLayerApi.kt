package io.github.mmolosay.datalayercommunication.communication.impl.mappers

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.communication.model.Capability

internal fun Capability.toDataLayer(): String =
    this.value

internal fun OnCapabilityChangedCallback.toDataLayer(): OnCapabilityChangedListener =
    OnCapabilityChangedListener { info ->
        val capability = Capability(info.name)
        val nodes = info.nodes.toNodes()
        onCapabilityChanged(capability, nodes)
    }