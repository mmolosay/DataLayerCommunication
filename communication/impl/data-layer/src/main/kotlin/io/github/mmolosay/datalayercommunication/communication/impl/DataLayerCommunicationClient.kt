package io.github.mmolosay.datalayercommunication.communication.impl

import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.failures.GenericCommunicationFailure
import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.models.Destination
import io.github.mmolosay.datalayercommunication.communication.rpc.RequestEncoder
import io.github.mmolosay.datalayercommunication.communication.rpc.ResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.rpc.request.Request
import io.github.mmolosay.datalayercommunication.communication.rpc.response.Response
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

    override suspend fun <R : Response> request(
        destination: Destination,
        request: Request,
    ): Resource<R> =
        runCatching {
            val requestData = encoder.encode(request)
            val responseBytes = gmsMessageClient
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
                GenericCommunicationFailure()
            },
        )
}