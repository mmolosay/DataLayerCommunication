package io.github.mmolosay.datalayercommunication.communication.models

/**
 * Set of [Path]s.
 *
 * @param [requests] Path for all RPC requests. They will be differentiated by checking actual type of Request.
 */
class PathSet(
    val requests: Path,
)