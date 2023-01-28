package com.example.di.mobile

import android.content.Context
import com.example.data.communication.DataLayerCommunicationClient
import com.example.data.communication.DataLayerNodeProvider
import com.example.data.communication.RepositoryResponseServer
import com.example.data.communication.convertion.CompressingDecorator
import com.example.data.communication.convertion.SerializationRequestDecoder
import com.example.data.communication.convertion.SerializationRequestEncoder
import com.example.data.communication.convertion.SerializationResponseDecoder
import com.example.data.communication.convertion.SerializationResponseEncoder
import com.example.data.communication.convertion.UncompressingDecorator
import com.example.domain.AnimalsRepository
import com.example.domain.communication.CommunicationClient
import com.example.domain.communication.NodeProvider
import com.example.domain.communication.convertion.RequestDecoder
import com.example.domain.communication.convertion.RequestEncoder
import com.example.domain.communication.convertion.ResponseDecoder
import com.example.domain.communication.convertion.ResponseEncoder
import com.example.domain.communication.server.CommunicationServer
import com.example.domain.communication.server.CommunicationServerImpl
import com.example.domain.communication.server.ResponseServer
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        UncompressingDecorator(SerializationRequestDecoder())

    @Provides
    @Singleton
    fun provideResponseDecoder(): ResponseDecoder =
        UncompressingDecorator(SerializationResponseDecoder())

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
}