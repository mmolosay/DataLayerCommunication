package com.example.domain.communication.model.request

import kotlinx.serialization.Serializable

/**
 * Request to be sent. Describes data that is inteded to be fetched.
 */
// request is parameter object. Parameters come from parameters of corresponding Repository method.
@Serializable
sealed class Request