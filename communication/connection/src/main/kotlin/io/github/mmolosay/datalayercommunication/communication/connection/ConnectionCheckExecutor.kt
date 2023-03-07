package io.github.mmolosay.datalayercommunication.communication.connection

/**
 * Executes a one-shot connection check between current device and one, specified by implementation.
 */
fun interface ConnectionCheckExecutor {

    /**
     * @return whether devices are connected or not.
     */
    suspend fun areDevicesConnected(): Boolean
}