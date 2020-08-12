package com.hex.shaadi.utils

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

fun <T> LifecycleOwner.subscribe(liveData: LiveData<T>, block: (T) -> Unit) {
    liveData.observe(this, Observer { block.invoke(it) })
}

fun Throwable.rethrowIfCancellation(block: ((Throwable) -> Unit)? = null) {
    if (this is CancellationException) {
        block?.invoke(this)
        throw this
    }
}

suspend fun withProgress(loading: MutableLiveData<Boolean>, block: suspend () -> Unit) {
    try {
        withContext(Dispatchers.Main) { loading.value = true }
        block.invoke()
        withContext(Dispatchers.Main) { loading.value = false }
    } catch (t: Throwable) {
        t.rethrowIfCancellation()
        withContext(Dispatchers.Main) { loading.value = false }
        throw t
    }
}
