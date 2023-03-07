package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.failures.CommunicationFailure
import io.github.mmolosay.datalayercommunication.communication.model.Data
import io.github.mmolosay.datalayercommunication.communication.model.Destination
import io.github.mmolosay.datalayercommunication.communication.models.request.Request
import io.github.mmolosay.datalayercommunication.communication.models.response.Response
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.success
import kotlinx.coroutines.tasks.await
import com.google.android.gms.wearable.MessageClient as GmsMessageClient

/**
 * Implementation of [CommunicationClient], powered by Google's Data Layer API.
 */
class DataLayerCommunicationClient(
    private val encoder: RequestEncoder,
    private val decoder: ResponseDecoder,
    private val gmsMessageClient: GmsMessageClient,
) : CommunicationClient {

    override suspend fun <R : io.github.mmolosay.datalayercommunication.communication.models.response.Response> request(
        destination: Destination,
        request: io.github.mmolosay.datalayercommunication.communication.models.request.Request,
    ): Resource<R> =
        runCatching {
            val requestData = encoder.encode(request)
            val responseBytes = gmsMessageClient
                .sendRequest(destination.nodeId, destination.path.value, requestData.bytes)
                .await()
            val responseData = Data(responseBytes)
            decoder.decode(responseData) as R // caller's responsibility to provide correct type
        }.toResource()

    private fun <R : io.github.mmolosay.datalayercommunication.communication.models.response.Response> Result<R>.toResource(): Resource<R> =
        fold(
            onSuccess = {
                Resource.success(it)
            },
            onFailure = { // you can check type of exception here and return different failures
                CommunicationFailure()
            },
        )
}