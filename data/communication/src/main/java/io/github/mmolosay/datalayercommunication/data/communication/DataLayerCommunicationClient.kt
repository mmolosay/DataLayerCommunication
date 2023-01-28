package io.github.mmolosay.datalayercommunication.data.communication

import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.Destination
import io.github.mmolosay.datalayercommunication.domain.communication.model.Message
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
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