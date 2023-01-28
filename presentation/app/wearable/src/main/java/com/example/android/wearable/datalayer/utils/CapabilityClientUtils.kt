package com.example.android.wearable.datalayer.utils

import android.net.Uri
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.wearable.CapabilityClient
import com.google.android.gms.wearable.CapabilityClient.OnCapabilityChangedListener

object CapabilityClientUtils {

    fun CapabilityClient.bindToLifecycle(
        lifecycleOwner: LifecycleOwner,
        listener: OnCapabilityChangedListener,
    ) =
        bindToLifecycle(lifecycleOwner.lifecycle, listener)

    fun CapabilityClient.bindToLifecycle(
        lifecycle: Lifecycle,
        listener: OnCapabilityChangedListener,
    ) {
        val observer = makeLifecycleObserver(this, listener)
        lifecycle.addObserver(observer)
    }

    private fun makeLifecycleObserver(
        capabilityClient: CapabilityClient,
        listener: OnCapabilityChangedListener,
    ): LifecycleObserver =
        object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                capabilityClient.addListener(
                    listener,
                    Uri.parse("wear://"),
                    CapabilityClient.FILTER_REACHABLE
                )
            }

            override fun onPause(owner: LifecycleOwner) {
                capabilityClient.removeListener(listener)
            }
        }
}