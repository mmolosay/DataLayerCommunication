package io.github.mmolosay.datalayercommunication.di.wearable

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.wearable.usecase.CheckIsHandheldDeviceConnectedUseCase
import io.github.mmolosay.datalayercommunication.domain.wearable.usecase.CheckIsHandheldDeviceConnectedUseCase.OnHandheldDeviceConnected
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {

    @Provides
    @Singleton
    fun provideCheckIsHandheldDeviceConnectedUseCase(
        nodeProvider: NodeProvider,
        onHandheldDeviceConnected: OnHandheldDeviceConnected,
    ) =
        CheckIsHandheldDeviceConnectedUseCase(
            nodeProvider = nodeProvider,
            onHandheldDeviceConnected = onHandheldDeviceConnected,
        )
}