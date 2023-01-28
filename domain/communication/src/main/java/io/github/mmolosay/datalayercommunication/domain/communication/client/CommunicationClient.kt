package io.github.mmolosay.datalayercommunication.domain.communication.client

import io.github.mmolosay.datalayercommunication.domain.communication.model.Destination
import io.github.mmolosay.datalayercommunication.domain.communication.model.Message
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response

/**
 * Exposes an API for components to communicate with [Destination]s in different ways.
 */
interface CommunicationClient {

    suspend fun sendMessage(
        destination: Destination,
        message: Message,
    ): Result<Unit>

    suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Result<R>
}