# DataLayerCommunication
Communication between handheld and wearable devices with Google's [Data Layer API](https://developer.android.com/training/wearables/data/data-layer#send-and-sync-with-API).

## Architecture
This project employs [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with separation by feature.

## Modules

### `:app:handheld`
Android application for handheld devices, like mobile phones and tablets. Can retrieve data both for itself and on request from wearable app.

### `:app:wearable`
Android application for wearable devices, like watches. Requests data from handheld app.

### `:di`
Dependency Injection, to be used in application modules.

 - `common` DI of components, common for both applications. Communication components, in our case.
 - `handheld` DI for handheld-specific app, like components from [`:data:handheld`](#data).
 - `wearable` DI for wearable-specific app, like components from [`:data:wearable`](#data).
 
### `:data`
Data sources.

- `common` application-independent, common for both apps data. Presently's empty, but can be populated with e.g. Repositories, that use `SharedPreferences`.
- `handheld` handheld-specific implementations of Repositories, that store data in memory.
- `wearable` wearable-specific implementations of Repositories, that issues requests to app on paired handheld device.

### `:domain`
Business models and application use cases.

- `common` models and use cases, common for both apps.
- `handheld` handheld-specific models and use cases. Presently's empty, but can be populated with e.g. `CheckIsPairedToWearableUseCase`.
- `wearable` wearable-specific models and use cases. Presently's empty, but can be populated with e.g. `CheckIsMobileAppInstalledOnPairedDeviceUseCase`.

### `:communication`
High-level, platform-independent abstractions of communication-related components.

- `impl:data-layer` implementation of `:communication`, powered by Google's [Data Layer API](https://developer.android.com/training/wearables/data/data-layer#send-and-sync-with-API).

### `utils`
Utility components, like mappers, extensions and structures.

- `resource` a [@Serialiazable](https://github.com/Kotlin/kotlinx.serialization) [monad](https://en.wikipedia.org/wiki/Monad_(functional_programming)), used to encapsulate values and propagate business-important failures to presentation layer.
