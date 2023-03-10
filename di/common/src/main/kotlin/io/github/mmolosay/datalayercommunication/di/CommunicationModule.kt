package io.github.mmolosay.datalayercommunication.di

import android.content.Context
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.CommunicationClient
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.convertion.RequestDecoder
import io.github.mmolosay.datalayercommunication.communication.convertion.RequestEncoder
import io.github.mmolosay.datalayercommunication.communication.convertion.ResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.convertion.ResponseEncoder
import io.github.mmolosay.datalayercommunication.communication.impl.ConvertingCommunicationServer
import io.github.mmolosay.datalayercommunication.communication.impl.DataLayerCapabilityClient
import io.github.mmolosay.datalayercommunication.communication.impl.DataLayerCommunicationClient
import io.github.mmolosay.datalayercommunication.communication.impl.DataLayerNodeProvider
import io.github.mmolosay.datalayercommunication.communication.impl.RepositoryResponseServer
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.Converters
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.ConvertersFactory.Feature
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.ConvertersFactory.add
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.RequestConverters
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.ResponseConverters
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.SerializationRequestDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.decode.SerializationResponseDecoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.SerializationRequestEncoder
import io.github.mmolosay.datalayercommunication.communication.impl.convertion.encode.SerializationResponseEncoder
import io.github.mmolosay.datalayercommunication.communication.models.Capability
import io.github.mmolosay.datalayercommunication.communication.models.CapabilitySet
import io.github.mmolosay.datalayercommunication.communication.models.Path
import io.github.mmolosay.datalayercommunication.communication.models.PathSet
import io.github.mmolosay.datalayercommunication.communication.server.CommunicationServer
import io.github.mmolosay.datalayercommunication.communication.server.ResponseServer
import io.github.mmolosay.datalayercommunication.domain.models.ModelSerializersModuleFactory
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.ResourceSerialializersModuleFactory
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import javax.inject.Singleton
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient
import com.google.android.gms.wearable.MessageClient as GmsMessageClient
import com.google.android.gms.wearable.NodeClient as GmsNodeClient
import io.github.mmolosay.datalayercommunication.communication.impl.datalayer.R as DataLayerR
import io.github.mmolosay.datalayercommunication.communication.impl.datalayer.service.R as DataLayerServiceR

/**
 * Dagger [Module], that provides communication associated dependencies.
 */
@Module
@InstallIn(SingletonComponent::class)
class CommunicationModule {

    // region Data Layer Components

    @Provides
    @Singleton
    fun provideGmsCapabilityClient(
        @ApplicationContext context: Context,
    ): GmsCapabilityClient =
        Wearable.getCapabilityClient(context)

    @Provides
    @Singleton
    fun provideGmsMessageClient(
        @ApplicationContext context: Context,
    ): GmsMessageClient =
        Wearable.getMessageClient(context)

    @Provides
    @Singleton
    fun provideGmsNodeClient(
        @ApplicationContext context: Context,
    ): GmsNodeClient =
        Wearable.getNodeClient(context)

    // endregion

    // region Convertion

    @Provides
    @Singleton
    fun provideStringFormat(): StringFormat {
        val modelsModule = ModelSerializersModuleFactory.make()
        val resourceModule = ResourceSerialializersModuleFactory.make()
        return Json { serializersModule = modelsModule + resourceModule }
    }

    @Provides
    @Singleton
    fun provideRequestConverters(
        format: StringFormat,
    ): RequestConverters =
        Converters(
            encoder = SerializationRequestEncoder(format),
            decoder = SerializationRequestDecoder(format),
        ).add(
            Feature.Compression,
        )

    @Provides
    @Singleton
    fun provideResponseConverters(
        format: StringFormat,
    ): ResponseConverters =
        Converters(
            encoder = SerializationResponseEncoder(format),
            decoder = SerializationResponseDecoder(format),
        ).add(
            Feature.Compression,
        )

    @Provides
    @Singleton
    fun provideRequestEncoder(
        converters: RequestConverters,
    ): RequestEncoder =
        converters.encoder

    @Provides
    @Singleton
    fun provideResponseEncoder(
        converters: ResponseConverters,
    ): ResponseEncoder =
        converters.encoder

    @Provides
    @Singleton
    fun provideRequestDecoder(
        converters: RequestConverters,
    ): RequestDecoder =
        converters.decoder

    @Provides
    @Singleton
    fun provideResponseDecoder(
        converters: ResponseConverters,
    ): ResponseDecoder =
        converters.decoder

    // endregion

    // region Components

    @Provides
    @Singleton
    fun provideNodeProvider(
        gmsCapabilityClient: GmsCapabilityClient,
        gmsNodeClient: GmsNodeClient,
        capabilities: CapabilitySet,
    ): NodeProvider =
        DataLayerNodeProvider(
            gmsCapabilityClient = gmsCapabilityClient,
            gmsNodeClient = gmsNodeClient,
            handheldCapability = capabilities.handheld,
            wearableCapability = capabilities.wearable,
        )

    @Provides
    @Singleton
    fun provideCommunicationClient(
        encoder: RequestEncoder,
        decoder: ResponseDecoder,
        gmsMessageClient: GmsMessageClient,
    ): CommunicationClient =
        DataLayerCommunicationClient(
            encoder = encoder,
            decoder = decoder,
            gmsMessageClient = gmsMessageClient,
        )

    @Provides
    @Singleton
    fun provideCommunicationServer(
        decoder: RequestDecoder,
        encoder: ResponseEncoder,
        responseServer: ResponseServer,
    ): CommunicationServer =
        ConvertingCommunicationServer(
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
    fun provideCapabilityClient(
        gmsCapabilityClient: GmsCapabilityClient,
    ): CapabilityClient =
        DataLayerCapabilityClient(
            gmsCapabilityClient = gmsCapabilityClient,
        )

    // endregion

    // region Utility sets

    @Provides
    @Singleton
    fun provideCommunicationPaths(
        @ApplicationContext context: Context,
    ): PathSet =
        PathSet(
            requests = Path(context.getString(DataLayerServiceR.string.communication_path_requests)),
        )

    @Provides
    @Singleton
    fun provideCommunicationCapabilities(
        @ApplicationContext context: Context,
    ): CapabilitySet =
        CapabilitySet(
            handheld = Capability(context.getString(DataLayerR.string.communication_capabililty_handheld)),
            wearable = Capability(context.getString(DataLayerR.string.communication_capabililty_wearable)),
        )

    // endregion
}