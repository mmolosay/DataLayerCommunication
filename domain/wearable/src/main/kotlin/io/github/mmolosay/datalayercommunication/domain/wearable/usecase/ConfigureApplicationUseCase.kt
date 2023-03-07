package io.github.mmolosay.datalayercommunication.domain.wearable.usecase

import io.github.mmolosay.datalayercommunication.domain.wearable.data.NodeRepository
import io.github.mmolosay.datalayercommunication.utils.resource.Resource
import io.github.mmolosay.datalayercommunication.utils.resource.getOrElse
import io.github.mmolosay.datalayercommunication.utils.resource.success
import javax.inject.Inject

class ConfigureApplicationUseCase @Inject constructor(
    private val nodeRepository: NodeRepository,
) {

    suspend operator fun invoke(): Resource<Unit> {
        val node = nodeRepository
            .getConnectedHandheldNode()
            .getOrElse { return it }
        nodeRepository.storeNode(node)
        return Resource.success()
    }
}