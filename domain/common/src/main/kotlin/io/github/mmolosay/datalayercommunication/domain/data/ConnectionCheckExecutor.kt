package io.github.mmolosay.datalayercommunication.domain.data

/**
 * Executes a one-shot connection check between current node and one, specified by implementation.
 */
fun interface ConnectionCheckExecutor {

    /**
     * @return whether nodese are connected or not.
     */
    suspend fun areNodesConnected(): Boolean
}