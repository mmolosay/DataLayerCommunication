package io.github.mmolosay.datalayercommunication.communication.connection

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow

/**
 * Provides state of connection between current device and other one, specified in implementation.
 */
interface ConnectionStateProvider {

    /**
     * Flow of connection state, with `true` emitted when device connects
     * and `false` when it disconnects.
     */
    val connectionStateFlow: Flow<Boolean>

    /**
     * Starts executing connection checks in some interval, or observing its changes, using observer
     * pattern.
     *
     * This method will launch coroutine in new [Job] and return it.
     *
     * @return [Job] of launched coroutine.
     */
    fun start(coroutineScope: CoroutineScope): Job

    fun stop()
}
