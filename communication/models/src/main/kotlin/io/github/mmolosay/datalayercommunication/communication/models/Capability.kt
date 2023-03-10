package io.github.mmolosay.datalayercommunication.communication.models

/**
 * Identifier used to specify a capability of a specific node.
 * Capabilities are used to distinguish between nodes and find an appropriate one(s).
 */
@JvmInline
value class Capability(val value: String)