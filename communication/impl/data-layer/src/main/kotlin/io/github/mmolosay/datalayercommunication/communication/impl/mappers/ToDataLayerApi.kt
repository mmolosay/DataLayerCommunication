package io.github.mmolosay.datalayercommunication.communication.impl.mappers

import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient.OnCapabilityChangedCallback
import io.github.mmolosay.datalayercommunication.communication.model.Capability

internal fun Capability.toDataLayer(): String = // TODO: check module and replace with this
    this.value

internal fun OnCapabilityChangedCallback.toDataLayer(): OnCapabilityChangedListener =
    OnCapabilityChangedListener { info ->
        val capability = Capability(info.name)
        val nodes = info.nodes.map { it.toNode() }
        onCapabilityChanged(capability, nodes)
    }