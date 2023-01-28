package com.example.domain.communication

import com.example.domain.communication.model.Destination
import com.example.domain.communication.model.Message
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response

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