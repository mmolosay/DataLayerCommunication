package io.github.mmolosay.datalayercommunication.domain.communication.client

import io.github.mmolosay.datalayercommunication.domain.communication.model.Destination
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response

/**
 * Exposes an API for components to communicate with [Destination]s in different ways.
 */
interface CommunicationClient {

    /**
     * Executes asynchronous request to [destination] with specified [request].
     */
    suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Result<R>
}