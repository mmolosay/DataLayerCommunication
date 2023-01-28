package com.example.android.wearable.datalayer

import android.util.Log
import com.example.domain.communication.model.Data
import com.example.domain.communication.server.CommunicationServer
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.TaskCompletionSource
import com.google.android.gms.wearable.WearableListenerService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MobileDataLayerService : WearableListenerService() {

    @Inject
    lateinit var server: CommunicationServer

    private val scopeJob = SupervisorJob()
    private val coroutineScope = CoroutineScope(Dispatchers.Default + scopeJob)

    override fun onDestroy() {
        super.onDestroy()
        /*
         * After 'onRequest()' had returned, service will be destroyed.
         * Unlike for UI, we don't want to cancel 'coroutineScope' once it happens, but rather
         * to finish all requests and return responses.
         */
    }

    override fun onRequest(p0: String, path: String, requestBytes: ByteArray): Task<ByteArray> {
        Log.d("MobileDataLayerService", "p:$p0 path:$path data:${String(requestBytes)}")
        val taskCompletionSource = TaskCompletionSource<ByteArray>()
        coroutineScope.launch {
            val requestData = Data(requestBytes)
            val responseData = server.on(requestData)
            taskCompletionSource.setResult(responseData.bytes)
        }
        return taskCompletionSource.task
    }
}