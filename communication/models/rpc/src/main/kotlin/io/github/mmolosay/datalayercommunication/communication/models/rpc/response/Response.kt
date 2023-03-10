package io.github.mmolosay.datalayercommunication.communication.models.rpc.response

import kotlinx.serialization.Serializable

/**
 * Response to be received from another device.
 * Should normally have a `Request` counterpart with required input.
 */
@Serializable
sealed class Response