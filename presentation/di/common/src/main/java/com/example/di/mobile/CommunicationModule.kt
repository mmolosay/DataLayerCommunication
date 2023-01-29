package com.example.di.mobile

import android.content.Context
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.data.communication.DataLayerCommunicationClient
import io.github.mmolosay.datalayercommunication.data.communication.DataLayerNodeProvider
import io.github.mmolosay.datalayercommunication.data.communication.RepositoryResponseServer
import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.DecompressingDecorator
import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.SerializationRequestDecoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.decode.SerializationResponseDecoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.CompressingDecorator
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.SerializationRequestEncoder
import io.github.mmolosay.datalayercommunication.data.communication.convertion.encode.SerializationResponseEncoder
import io.github.mmolosay.datalayercommunication.di.R
import io.github.mmolosay.datalayercommunication.domain.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.domain.communication.client.CommunicationClient
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.domain.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.domain.communication.model.CommunicationPaths
import io.github.mmolosay.datalayercommunication.domain.communication.model.Path
import io.github.mmolosay.datalayercommunication.domain.communication.server.CommunicationServer
import io.github.mmolosay.datalayercommunication.domain.communication.server.CommunicationServerImpl
import io.github.mmolosay.datalayercommunication.domain.communication.server.ResponseServer
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import javax.inject.Singleton

/**
 * Dagger [Module], that provides communication associated dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    @Provides
    @Singleton
    fun provideRequestEncoder(): RequestEncoder =
        CompressingDecorator(SerializationRequestEncoder())

    @Provides
    @Singleton
    fun provideResponseEncoder(): ResponseEncoder =
        CompressingDecorator(SerializationResponseEncoder())

    @Provides
    @Singleton
    fun provideRequestDecoder(): RequestDecoder =
        DecompressingDecorator(SerializationRequestDecoder())

    @Provides
    @Singleton
    fun provideResponseDecoder(): ResponseDecoder =
        DecompressingDecorator(SerializationResponseDecoder())

    @Provides
    @Singleton
    fun provideNodeProvider(
        @ApplicationContext context: Context,
    ): NodeProvider =
        DataLayerNodeProvider(
            capabilityClient = Wearable.getCapabilityClient(context),
            mobileCapability = "mobile", // TODO: try obtaining string from resources, placing .xml file in this module
            wearableCapability = "wear", // TODO: try obtaining string from resources, placing .xml file in this module
        )

    @Provides
    @Singleton
    fun provideCommunicationClient(
        @ApplicationContext context: Context,
        encoder: RequestEncoder,
        decoder: ResponseDecoder,
    ): CommunicationClient =
        DataLayerCommunicationClient(
            encoder = encoder,
            decoder = decoder,
            messageClient = Wearable.getMessageClient(context),
        )

    @Provides
    @Singleton
    fun provideCommunicationServer(
        decoder: RequestDecoder,
        encoder: ResponseEncoder,
        responseServer: ResponseServer,
    ): CommunicationServer =
        CommunicationServerImpl(
            decoder = decoder,
            encoder = encoder,
            responseServer = responseServer,
        )

    @Provides
    @Singleton
    fun provideResponseServer(
        animalsRepository: AnimalsRepository,
    ): ResponseServer =
        RepositoryResponseServer(
            animalsRepository = animalsRepository,
        )

    @Provides
    @Singleton
    fun provideCommunicationPaths(
        @ApplicationContext context: Context,
    ): CommunicationPaths =
        CommunicationPaths(
            getAllAnimals = Path(context.getString(R.string.communication_path_get_all_animals)),
            deleteRandomAnimalById = Path(context.getString(R.string.communication_path_delete_random_animal_by_id)),
        )
}