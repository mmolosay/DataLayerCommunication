package com.example.data.communication

import com.example.domain.communication.CommunicationClient
import com.example.domain.communication.convertion.RequestEncoder
import com.example.domain.communication.convertion.ResponseDecoder
import com.example.domain.communication.model.Data
import com.example.domain.communication.model.Destination
import com.example.domain.communication.model.Message
import com.example.domain.communication.model.request.Request
import com.example.domain.communication.model.response.Response
import com.google.android.gms.wearable.MessageClient
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [CommunicationClient], powered by Google's Data Layer API.
 */
class DataLayerCommunicationClient(
    private val encoder: RequestEncoder,
    private val decoder: ResponseDecoder,
    private val messageClient: MessageClient,
) : CommunicationClient {

    override suspend fun sendMessage(
        destination: Destination,
        message: Message,
    ): Result<Unit> =
        runCatching {
            // TODO: send using MessageClient
        }

    override suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Result<R> =
        runCatching {
            val requestData = encoder.encode(request)
            val responseData = messageClient
                .sendRequest(
                    destination.nodeId,
                    destination.path,
                    requestData.bytes,
                )
                .await()
                .let { Data(it) }
            decoder.decode(responseData) as R
        }
}