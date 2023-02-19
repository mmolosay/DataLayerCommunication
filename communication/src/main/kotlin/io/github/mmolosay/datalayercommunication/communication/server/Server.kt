package io.github.mmolosay.datalayercommunication.communication.server

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface Server<Request, Response> {
    suspend fun reciprocate(request: Request): Response
}