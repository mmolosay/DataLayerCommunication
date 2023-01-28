package com.example.domain.communication

import com.example.domain.communication.model.Node

/**
 * Exposes an API for obtaining [Node]s in current device network.
 */
interface NodeProvider {
    suspend fun mobile(): Collection<Node>
    suspend fun wearable(): Collection<Node>
}