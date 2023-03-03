package io.github.com.mmolosay.datalayercommunication.communication.connection.impl

import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.measureTime

abstract class AbstractConnectionCheckExecutor : ConnectionCheckExecutor {

    private val mutableFlow =
        MutableSharedFlow<Boolean>(replay = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val connectionStateFlow: Flow<Boolean> =
        mutableFlow.distinctUntilChanged()

    private var repeatingConnectionCheckJob: Job? = null
    private var oneTimeConnectionCheckJob: Job? = null

    private var lastUsedInterval: Duration? = null

    @OptIn(ExperimentalTime::class)
    override fun launchRepeatingConnectionCheck(
        coroutineScope: CoroutineScope,
        connectionCheckInterval: Duration,
    ): Job {
        val job = Job().also { repeatingConnectionCheckJob = it }
        lastUsedInterval = connectionCheckInterval
        return coroutineScope.launch(context = job) {
            while (job.isActive) {
                val elapsed = measureTime {
                    executeConnectionCheckAsync()
                        .await()
                        .let { mutableFlow.tryEmit(it) }
                }
                println(elapsed.inWholeMilliseconds)
                val left = connectionCheckInterval - elapsed
                delay(left) // skipped if <= 0
            }
        }
    }

    override fun restartRepeatingConnectionCheck(
        coroutineScope: CoroutineScope,
    ): Job {
        val interval = requireNotNull(lastUsedInterval)
        repeatingConnectionCheckJob?.cancel()
        return launchRepeatingConnectionCheck(coroutineScope, interval)
    }

    private fun CoroutineScope.executeConnectionCheckAsync(): Deferred<Boolean> {
        val job = Job(parent = repeatingConnectionCheckJob)
            .also { oneTimeConnectionCheckJob = it }
        return async(context = job) {
            executeConnectionCheck()
        }
    }

    abstract suspend fun executeConnectionCheck(): Boolean
}