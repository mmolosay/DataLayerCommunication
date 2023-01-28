package com.example.domain.communication.model

/**
 * Destination of a message to be sent to.
 */
data class Destination(
    val nodeId: String,
    val path: String,
)