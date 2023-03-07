package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toDataLayer
import io.github.mmolosay.datalayercommunication.communication.impl.mappers.toNode
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.model.Node
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration.Companion.seconds
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

/**
 * Implementation of [NodeProvider], powered by Google's Data Layer API.
 */
class DataLayerNodeProvider(
    private val gmsCapabilityClient: GmsCapabilityClient,
    private val handheldCapability: Capability,
    private val wearableCapability: Capability,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO,
) : NodeProvider {

    override suspend fun handheld(): Collection<Node> =
        getNodesWithCapability(handheldCapability)

    override suspend fun wearable(): Collection<Node> =
        getNodesWithCapability(wearableCapability)

    private suspend fun getNodesWithCapability(capability: Capability): Collection<Node> =
        withContext(dispatcher) {
            val capabilityInfo = withTimeoutOrNull(3.seconds) {
                gmsCapabilityClient
                    .getCapability(capability.toDataLayer(), GmsCapabilityClient.FILTER_REACHABLE)
                    .await()
            }
            capabilityInfo
                ?.nodes
                ?.map { it.toNode() }
                ?: emptyList()
        }


}