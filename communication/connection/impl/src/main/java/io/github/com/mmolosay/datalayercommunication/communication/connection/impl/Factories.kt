package io.github.com.mmolosay.datalayercommunication.communication.connection.impl

import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor

typealias CheckConnection = suspend () -> Boolean

fun ConnectionCheckExecutor(
    checkConnection: CheckConnection,
): ConnectionCheckExecutor =
    object : AbstractConnectionCheckExecutor() {
        override suspend fun executeConnectionCheck(): Boolean =
            checkConnection()
    }