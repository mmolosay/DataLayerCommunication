# DataLayerCommunication
Communication between handheld and wearable devices with Google's [Data Layer API](https://developer.android.com/training/wearables/data/data-layer#send-and-sync-with-API), made in __right way__.

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

Each module, its architectural layer and contained components is described in its own _README.md_.

## Issues
Looks like there's an issue inside `com.google.android.gms:play-services-wearable`.'

Steps:
1. remove repeating connection check `launchRepeatingConnectionCheck()` from `init` block; you can replace it with just `launchConnectionCheck()`.
2. start app with connected handheld device.
3. disconnect handheld device.
4. call 'getAllAnimals()' method, or any other, involving __Data Layer API__.
 
Launched coroutine will get "stuck", and will finish only after reconnecting handheld device back.
However, after few seconds, calling `getAllAnimals()` method (or any other, as said earlier), will run its coroutine
as expected. Coroutines before this interval had passed will get "stuck" as well.

Placing logging inside `getAllAnimals()` in first line of coroutine and after execution of usecase, will produce following loggs:
``` text
21:56:17.651  D  getAllAnimals: coroutine launched    ← first call to `getAllAnimals()`
21:56:33.786  D  getAllAnimals: coroutine launched    ← second call to 'getAllAnimals()'
21:56:33.789  D  getAllAnimals: use cases executed    ← second call executed successfully
21:57:41.642  E  ANR in com.google.android.gms.persistent
                 PID: 7541
                 Reason: Broadcast of Intent { act=com.google.android.gms.wearable.EMULATOR flg=0x400010 (has extras) }
                 Load: 0.0 / 0.0 / 0.0
                 ----- Output from /proc/pressure/memory -----
                 some avg10=0.00 avg60=0.00 avg300=0.00 total=260591
                 full avg10=0.00 avg60=0.00 avg300=0.00 total=115912
                 ----- End output from /proc/pressure/memory -----
                 ~~~ Here goes omitted details on CPU usage ~~~
21:57:41.655  D  getAllAnimals: use cases executed    ← first call unstuck after exception
```
