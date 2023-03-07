package io.github.mmolosay.datalayercommunication.communication.server

import io.github.mmolosay.datalayercommunication.communication.models.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.response.Response

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface ResponseServer : Server<Request, Response>