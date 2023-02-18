package io.github.mmolosay.datalayercommunication.domain.communication

import io.github.mmolosay.datalayercommunication.domain.resource.Resource
import kotlinx.serialization.Serializable

object CommunicationFailures {

    /**
     * Describes situation, when there is no node meeting specified requirements.
     */
    @Serializable
    object NoSuchNodeFailure : Resource.Failure

    /**
     * Describes situation, when communication between devices had failed.
     */
    @Serializable
    object CommunicatingFailure : Resource.Failure
}