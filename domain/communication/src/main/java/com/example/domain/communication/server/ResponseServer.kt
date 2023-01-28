package com.example.domain.communication.server

import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface ResponseServer : Server<Request, Response>