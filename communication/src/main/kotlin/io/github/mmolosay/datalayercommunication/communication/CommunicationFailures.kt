package io.github.mmolosay.datalayercommunication.communication

import io.github.mmolosay.datalayercommunication.utils.resource.Resource
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