package io.github.mmolosay.datalayercommunication.communication.impl

import com.google.android.gms.wearable.MessageClient
import io.github.mmolosay.datalayercommunication.domain.communication.CommunicationFailures.CommunicatingFailure
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.Data
import io.github.mmolosay.datalayercommunication.domain.communication.model.Destination
import io.github.mmolosay.datalayercommunication.domain.communication.model.request.Request
import io.github.mmolosay.datalayercommunication.domain.communication.model.response.Response
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.tasks.await

/**
 * Implementation of [CommunicationClient], powered by Google's Data Layer API.
 */
class DataLayerCommunicationClient(
    private val encoder: RequestEncoder,
    private val decoder: ResponseDecoder,
    private val messageClient: MessageClient,
) : CommunicationClient {

    override suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Resource<R> =
        runCatching {
            val requestData = encoder.encode(request)
            val responseBytes = messageClient
                .sendRequest(destination.nodeId, destination.path.value, requestData.bytes)
                .await()
            val responseData = Data(responseBytes)
            decoder.decode(responseData) as R // caller's responsibility to provide correct type
        }.toResource()

    private fun <R : Response> Result<R>.toResource(): Resource<R> =
        fold(
            onSuccess = {
                Resource.success(it)
            },
            onFailure = { // you can check type of exception here and return different failures
                CommunicatingFailure
            },
        )
}