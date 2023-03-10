package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toDataLayer
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toNodes
import io.github.mmolosay.datalayercommunication.communication.models.Capability
import io.github.mmolosay.datalayercommunication.communication.models.Node
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient
import com.google.android.gms.wearable.NodeClient as GmsNodeClient

/**
 * Implementation of [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val gmsNodeClient: GmsNodeClient,
    private val handheldCapability: Capability,
    private val wearableCapability: Capability,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NodeProvider {

    override suspend fun all(): Collection<Node>? =
        withContext(dispatcher) {
            withTimeoutOrNull(timeout) {
                gmsNodeClient
                    .connectedNodes
                    .await()
                    .toNodes()
            }
        }

    override suspend fun handheld(): Collection<Node>? =
        getNodesWithCapability(handheldCapability)

    override suspend fun wearable(): Collection<Node>? =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: Capability): Collection<Node>? =
        withContext(dispatcher) {
            val capabilityInfo = withTimeoutOrNull(timeout) {
                gmsCapabilityClient
                    .getCapability(capability.toDataLayer(), GmsCapabilityClient.FILTER_REACHABLE)
                    .await()
            }
            capabilityInfo
                ?.nodes
                ?.filter { it.isNearby } // is connected via Bluetooth
                ?.toNodes()
        }

    private companion object {
        val timeout = 3.seconds
    }
}