package io.github.mmolosay.datalayercommunication.communication.impl.datalayer.service

import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import io.github.mmolosay.datalayercommunication.communication.models.Data
import io.github.mmolosay.datalayercommunication.communication.server.CommunicationServer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * [WearableListenerService] from Google's Data Layer API, which employs [CommunicationServer]
 * in order to decypher incoming requests and return appropriate responses.
 *
 * This service, as all other, should be registered in
 * Android application module's `AndroidManifest.xml` file.
 */
@AndroidEntryPoint
class CommunicationService : WearableListenerService() {

    @Inject
    lateinit var server: CommunicationServer

    private val coroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

    override fun onDestroy() {
        super.onDestroy()
        /*
         * After 'onRequest()' had returned, service will be destroyed.
         * Unlike for UI destruction, we don't want to cancel 'coroutineScope' once it happens,
         * but rather to finish all requests and return responses.
         */
    }

    override fun onRequest(p0: String, path: String, requestBytes: ByteArray): Task<ByteArray> {
        val taskCompletionSource = TaskCompletionSource<ByteArray>()
        coroutineScope.launch {
            val requestData = Data(requestBytes)
            val responseData = server.reciprocate(requestData)
            taskCompletionSource.setResult(responseData.bytes)
        }
        return taskCompletionSource.task
    }
}