package net.novate.reader

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observe(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, Observer<T> { t -> observer(t) })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T) {
            removeObserver(this)
            observer(t)
        }
    })
}