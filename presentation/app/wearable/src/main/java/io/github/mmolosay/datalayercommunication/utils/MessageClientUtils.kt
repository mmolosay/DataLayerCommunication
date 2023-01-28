package io.github.mmolosay.datalayercommunication.utils

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.wearable.MessageClient
import com.google.android.gms.wearable.MessageClient.OnMessageReceivedListener

object MessageClientUtils {

    fun MessageClient.bindToLifecycle(
        lifecycleOwner: LifecycleOwner,
        listener: OnMessageReceivedListener,
    ) =
        bindToLifecycle(lifecycleOwner.lifecycle, listener)

    fun MessageClient.bindToLifecycle(
        lifecycle: Lifecycle,
        listener: OnMessageReceivedListener,
    ) {
        val observer = makeLifecycleObserver(this, listener)
        lifecycle.addObserver(observer)
    }

    private fun makeLifecycleObserver(
        messageClient: MessageClient,
        listener: OnMessageReceivedListener,
    ): LifecycleObserver =
        object : DefaultLifecycleObserver {

            override fun onResume(owner: LifecycleOwner) {
                messageClient.addListener(listener)
            }

            override fun onPause(owner: LifecycleOwner) {
                messageClient.removeListener(listener)
            }
        }
}