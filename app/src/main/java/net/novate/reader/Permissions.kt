package net.novate.reader

import android.util.SparseArray
import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.core.util.set
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent

object Permissions {

    private val requests = SparseArray<(IntArray) -> Unit>()

    @MainThread
    fun requestPermissions(activity: ComponentActivity, permissions: Array<out String>, callback: (IntArray) -> Unit) {
        requests[0] = callback
        activity.lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun onDestory() {
                requests.remove(0)
                activity.lifecycle.removeObserver(this)
            }
        })

    }

}