package io.github.mmolosay.datalayercommunication.communication.impl.factories

import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProviderFactory
import io.github.mmolosay.datalayercommunication.communication.impl.CapabilityListenerConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

/**
 * Implementation of [ConnectionStateProviderFactory], that employs [CapabilityListenerConnectionStateProvider].
 */
class ConnectionStateProviderFactoryImpl(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val handheldCapability: Capability,
    private val wearableCapability: Capability,
) : ConnectionStateProviderFactory {

    override fun createForHandheld(): ConnectionStateProvider =
        CapabilityListenerConnectionStateProvider(
            gmsCapabilityClient = gmsCapabilityClient,
            nodeCapability = handheldCapability,
        )

    override fun createForWearable(): ConnectionStateProvider =
        CapabilityListenerConnectionStateProvider(
            gmsCapabilityClient = gmsCapabilityClient,
            nodeCapability = wearableCapability,
        )
}