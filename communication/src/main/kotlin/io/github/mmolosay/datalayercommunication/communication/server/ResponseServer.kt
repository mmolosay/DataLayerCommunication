package io.github.mmolosay.datalayercommunication.communication.server

import io.github.mmolosay.datalayercommunication.communication.models.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.rpc.response.Response

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface ResponseServer : Server<Request, Response>