package io.github.mmolosay.datalayercommunication.communication.impl.mappers

import io.github.mmolosay.datalayercommunication.communication.models.Node
import com.google.android.gms.wearable.Node as DataLayerNode

internal fun Iterable<DataLayerNode>.toNodes(): List<Node> =
    this.mapNotNull { it.toNodeOrNull() }

internal fun DataLayerNode.toNodeOrNull(): Node? {
    if (!this.isNearby) return null // Node is a connected device only
    return Node(
        id = this.id,
        name = this.displayName,
    )
}