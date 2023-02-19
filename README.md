# DataLayerCommunication
Communication between handheld and wearable devices with Google's [Data Layer API](https://developer.android.com/training/wearables/data/data-layer#send-and-sync-with-API), made in right way.

## Architecture
This project employs [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) with separation by feature.

## Approach
The idea behind implemented approach is to reuse as much of code as possible.

From application side, there should be no details on the data source, either it's being a remote server, local database, or paired device, that utilizes any of these sources.

Thus, we want to reuse business logic (use cases), but provide different data sources (Repositories) for handheld and wearable apps.
When running on handheld device, Repositories will do *"real"* data fetching (like from remote server, but in this demo it's just a in-memory store).
When running on wearable device, Repositories will request data from app on paired handheld device.

As you can see, with correctly designed components and their responsibilities, cross-device communication effectively becomes a task of just providing device-specific Repositories to shared use cases.

> **Note**
> In the following diagram some components of actual implementation, like Encoders, Decoders, ResponseServer, etc. are omitted.

![Untitled Diagram drawio (1)](https://user-images.githubusercontent.com/32337243/219979818-515e9078-011b-447f-ba69-16651814c48d.png)

## Modules

### `:app:handheld`
Android application for handheld devices, like mobile phones and tablets. Can retrieve data both for itself and on request from wearable app.

### `:app:wearable`
Android application for wearable devices, like watches. Requests data from handheld app.

### `:di`
Dependency Injection to be used by application modules.

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
