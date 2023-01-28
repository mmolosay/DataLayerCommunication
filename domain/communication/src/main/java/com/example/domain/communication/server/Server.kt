package com.example.domain.communication.server

/**
 * Serves appropriate [Response] for specified [Request].
 */
interface Server<Request, Response> {
    suspend fun on(request: Request): Response
}