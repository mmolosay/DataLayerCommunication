package io.github.mmolosay.datalayercommunication.communication.models.rpc.request

import kotlinx.serialization.Serializable

/**
 * Request to be sent to another device.
 * Should normally have a `Response` counterpart with retrieved data.
 */
// Request is parameter object: https://refactoring.guru/introduce-parameter-object
// Fiels of concrete Request class come from parameters of corresponding Repository method.
@Serializable
sealed class Request