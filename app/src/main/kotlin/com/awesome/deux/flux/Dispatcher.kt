package com.awesome.deux.flux

import kotlinx.coroutines.channels.BroadcastChannel

/**
 * Dispatcher
 */
class Dispatcher {

    /**
     * Event to notify "store".
     */
    val event = BroadcastChannel<Action>(1)

    /**
     * Dispatch event.
     *
     * @param action T
     */
    suspend fun dispatch(action: Action) {
        event.send(action)
    }
}