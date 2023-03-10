package io.github.mmolosay.datalayercommunication.communication.server

import io.github.mmolosay.datalayercommunication.communication.models.Data

/**
 * Serves appropriate response [Data] for specified request [Data].
 */
interface CommunicationServer : Server<Data, Data>
