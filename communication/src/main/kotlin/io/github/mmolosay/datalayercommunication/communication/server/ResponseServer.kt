package io.github.mmolosay.datalayercommunication.communication.server

import io.github.mmolosay.datalayercommunication.communication.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.rpc.response.Response

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface ResponseServer : Server<Request, Response>