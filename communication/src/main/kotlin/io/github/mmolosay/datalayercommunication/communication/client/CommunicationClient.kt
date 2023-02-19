package io.github.mmolosay.datalayercommunication.communication.client

import io.github.mmolosay.datalayercommunication.communication.model.Destination
import io.github.mmolosay.datalayercommunication.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.communication.model.response.Response
import io.github.mmolosay.datalayercommunication.utils.resource.Resource

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
    ): Resource<R>
}