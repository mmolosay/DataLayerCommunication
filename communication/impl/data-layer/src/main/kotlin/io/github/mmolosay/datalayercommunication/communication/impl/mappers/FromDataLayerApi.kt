package io.github.mmolosay.datalayercommunication.communication.impl.mappers

import io.github.mmolosay.datalayercommunication.communication.model.Node
import com.google.android.gms.wearable.Node as DataLayerNode

internal fun DataLayerNode.toNode(): Node =
    Node(
        id = this.id,
        name = this.displayName,
        isConnectedToCurrentDevice = this.isNearby,
    )