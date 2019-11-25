package com.awesome.deux.ui.post

import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.awesome.deux.App
import com.awesome.deux.action.post.PostAction
import com.awesome.deux.flux.Dispatcher
import com.awesome.deux.flux.Result
import com.awesome.deux.flux.Store
import com.awesome.deux.model.Post
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.mapNotNull

/**
 * Post List Screen's Store. [Store] subclass.
 *
 * @param app Application
 * @param dispatcher Dispatcher
 */
class PostStore(app: App, private val dispatcher: Dispatcher) : Store(app) {

    /**
     * Posts
     */
    val posts: LiveData<Result<List<Post>>> = dispatcher.event
        .asFlow()
        .mapNotNull { it as? PostAction.FetchedPosts }
        .mapNotNull { it.result }
        .asLiveData(scope.coroutineContext)
}
