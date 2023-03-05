package io.github.mmolosay.datalayercommunication.di

import android.content.Context
import com.google.android.gms.wearable.Wearable
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.mmolosay.datalayercommunication.communication.NodeProvider
import io.github.mmolosay.datalayercommunication.communication.client.CapabilityClient
import io.github.mmolosay.datalayercommunication.communication.client.CommunicationClient
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
import io.github.mmolosay.datalayercommunication.communication.model.Capability
import io.github.mmolosay.datalayercommunication.communication.model.CapabilitySet
import io.github.mmolosay.datalayercommunication.communication.model.Path
import io.github.mmolosay.datalayercommunication.communication.model.PathSet
import io.github.mmolosay.datalayercommunication.communication.server.CommunicationServer
import io.github.mmolosay.datalayercommunication.communication.server.ResponseServer
import io.github.mmolosay.datalayercommunication.domain.model.ModelSerializersModuleFactory
import io.github.mmolosay.datalayercommunication.domain.repository.AnimalsRepository
import io.github.mmolosay.datalayercommunication.utils.resource.ResourceSerialializersModuleFactory
import kotlinx.serialization.StringFormat
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.plus
import javax.inject.Singleton
import com.google.android.gms.wearable.CapabilityClient as GmsCapabilityClient

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
        capabilities: CapabilitySet,
    ): NodeProvider =
        DataLayerNodeProvider(
            gmsCapabilityClient = gmsCapabilityClient,
            handheldCapability = capabilities.handheld,
            wearableCapability = capabilities.wearable,
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
            getAllAnimals = Path(context.getString(R.string.communication_path_get_all_animals)),
            deleteRandomAnimalById = Path(context.getString(R.string.communication_path_delete_random_animal_by_id)),
        )

    @Provides
    @Singleton
    fun provideCommunicationCapabilities(
        @ApplicationContext context: Context,
    ): CapabilitySet =
        CapabilitySet(
            handheld = Capability(context.getString(R.string.communication_capabililty_handheld)),
            wearable = Capability(context.getString(R.string.communication_capabililty_wearable)),
        )

    // endregion
}