package io.github.mmolosay.datalayercommunication.communication.failures

import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import kotlinx.serialization.Serializable

/*
 * Resource.Failure-s, related to cross-device communication.
 */

/**
 * Describes situation, when connection with requested node(s) cannot be established.
 * It may happen, when requested node(s) disconnect from the current device.
 */
@Serializable
object ConnectionFailure : CommunicationFailure()


/**
 * Describes situation, when there is no node, meeting specified requirements.
 */
@Serializable
object NoSuchNodeFailure : CommunicationFailure()

/**
 * Describes situation, when communication between devices had failed.
 *
 * It is the most generic type of communication failure with no clear cause.
 * One should always prefer a derived, specific type of failure instead of this one.
 */
@Serializable
open class CommunicationFailure : Resource.Failure