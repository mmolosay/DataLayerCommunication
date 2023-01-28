package com.example.android.wearable.datalayer.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.wearable.DataClient
import com.google.android.gms.wearable.DataClient.OnDataChangedListener

object DataClientUtils {

    fun DataClient.bindToLifecycle(
        lifecycleOwner: LifecycleOwner,
        listener: OnDataChangedListener,
    ) =
        bindToLifecycle(lifecycleOwner.lifecycle, listener)

    fun DataClient.bindToLifecycle(
        lifecycle: Lifecycle,
        listener: OnDataChangedListener,
    ) {
        val observer = makeLifecycleObserver(this, listener)
        lifecycle.addObserver(observer)
    }

    private fun makeLifecycleObserver(
        dataClient: DataClient,
        listener: OnDataChangedListener,
    ): LifecycleObserver =
        object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                dataClient.addListener(listener)
            }

            override fun onPause(owner: LifecycleOwner) {
                dataClient.removeListener(listener)
            }
        }
}