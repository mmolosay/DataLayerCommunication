package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.com.mmolosay.datalayercommunication.communication.connection.impl.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.communication.connection.ConnectionCheckExecutor
import io.github.mmolosay.datalayercommunication.domain.wearable.repository.ConnectionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideHandheldConnectionCheckExecutor(
        connectionRepository: ConnectionRepository,
    ): ConnectionCheckExecutor =
        ConnectionCheckExecutor {
            connectionRepository.isConnectedToHandheldDevice()
        }
}