package io.github.mmolosay.datalayercommunication.communication.impl.mappers

import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

internal fun CapabilityClient.OnCapabilityChangedListener.toDataLayerListener(
    coroutineScope: CoroutineScope,
) =
    com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener { info ->
        val capability = Capability(info.name)
        val nodes = info.nodes.map { it.toNode() }
        coroutineScope.launch {
            onCapabilityChanged(capability, nodes)
        }
    }