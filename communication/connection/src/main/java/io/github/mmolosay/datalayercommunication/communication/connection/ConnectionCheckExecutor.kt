package io.github.mmolosay.datalayercommunication.communication.connection

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlin.time.Duration

/**
 * Executes check, whether some device is connected to the current one.
 */
interface ConnectionCheckExecutor {

    /**
     * Flow of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionStateFlow: Flow<Boolean>

    /**
     * Launches new coroutine, scoped to [coroutineScope], which will execute connection check in
     * specified [connectionCheckInterval] forever, until its [Job] is active.
     *
     * Results of connection check are emitted from [connectionStateFlow].
     *
     * Use [Job.cancel] in order to cancel connection check execution.
     *
     * @return new [Job], which is used in launched coroutine.
     */
    fun launchRepeatingConnectionCheck(
        coroutineScope: CoroutineScope,
        connectionCheckInterval: Duration,
    ): Job

    /**
     * Restarts repeating connection check in interval, that was used for latest [launchRepeatingConnectionCheck].
     * [Job] of last [launchRepeatingConnectionCheck] will be canceled, and new connection check
     * coroutine will be launched.
     *
     * This method can also be used when immediate connection check is requested.
     * Thus, we will not wait for currently running [launchRepeatingConnectionCheck] to finish
     * waiting for its interval to execute next connection check.
     * Instead, whole coroutine will be restarted, and we can be sure, that execution of next
     * connection check will be launched as soon as possible.
     *
     * @return new [Job], which is used in launched coroutine.
     */
    fun restartRepeatingConnectionCheck(
        coroutineScope: CoroutineScope,
    ): Job
}