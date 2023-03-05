package io.github.mmolosay.datalayercommunication.communication.impl.connection

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProvider
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionStateProviderFactory
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.singleConnectedHandheldNode
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

/**
 * Implementation of [ConnectionStateProviderFactory], that employs [CapabilityListenerConnectionStateProvider].
 */
class ConnectionStateProviderFactoryImpl(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val nodeProvider: NodeProvider,
    private val handheldCapability: Capability,
) : ConnectionStateProviderFactory {

    override fun createForHandheld(): ConnectionStateProvider =
        CapabilityListenerConnectionStateProvider(
            gmsCapabilityClient = gmsCapabilityClient,
            nodeCapability = handheldCapability,
            connectionCheckExecutor = makeHandheldConnectionCheckExecutor(),
        )

    override fun createForWearable(): ConnectionStateProvider =
        TODO("createForWearable is not implemented")

    private fun makeHandheldConnectionCheckExecutor() =
        ConnectionCheckExecutor {
            nodeProvider.singleConnectedHandheldNode().isSuccess
        }
}