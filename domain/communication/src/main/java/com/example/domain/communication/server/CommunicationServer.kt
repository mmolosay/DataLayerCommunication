package com.example.domain.communication.server

import com.example.domain.communication.model.Data

/**
 * Serves appropriate response [Data] for specified request [Data].
 */
interface CommunicationServer : Server<Data, Data>
