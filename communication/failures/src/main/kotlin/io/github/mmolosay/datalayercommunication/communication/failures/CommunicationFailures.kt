package io.github.mmolosay.datalayercommunication.communication.failures

import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

/**
 * [Resource.Failure]s, related to cross-device communication.
 */
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