package com.awesome.deux.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

fun <T> LiveData<T>.observeNotNull(owner: LifecycleOwner, observer: (T) -> Unit) {
    observe(owner, Observer {
        if (it != null) observer(it)
    })
}

fun <T> LiveData<T>.observeOrNull(owner: LifecycleOwner, observer: (T?) -> Unit) {
    observe(owner, Observer {
        observer(it)
    })
}

fun <T> LiveData<T>.observeOnChanged(owner: LifecycleOwner, observer: (T?) -> Unit) {
    var pending: T? = null
    observe(owner, Observer {
        if (pending?.equals(it) == false) {
            observer(it)
        }
        pending = it
    })
}