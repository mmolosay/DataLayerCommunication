package io.github.mmolosay.datalayercommunication.communication.server

import io.github.mmolosay.datalayercommunication.communication.model.Data

/**
 * Serves appropriate response [Data] for specified request [Data].
 */
interface CommunicationServer : Server<Data, Data>
