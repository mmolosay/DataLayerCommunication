## `:communication:impl:data-layer:service`

* Architectural layer: **Data**
* Abstractness: **Concrete**

> Contains `CommunicationService`, which is an Android's [Service](https://developer.android.com/guide/components/services) and subclass of `WearableListenerService` from Google's [Data Layer API](https://developer.android.com/training/wearables/data/data-layer).
>
> This module (and thus service) is being used by application modules (presently only by [`:app:handheld`](https://github.com/mmolosay/DataLayerCommunication/tree/master/app/handheld)).
