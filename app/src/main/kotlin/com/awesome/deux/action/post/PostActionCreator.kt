package com.awesome.deux.action.post

import com.awesome.deux.data.repository.PostRepository
import com.awesome.deux.data.repository.UserRepository
import com.awesome.deux.flux.ActionCreator
import com.awesome.deux.flux.Dispatcher
import com.awesome.deux.flux.Result
import com.awesome.deux.model.Post
import com.awesome.deux.model.User
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * PostAction's ActionCreator
 *
 * @property dispatcher Dispatcher
 * @property postRepository Post's Repository
 * @property userRepository User's Repository
 * @property coroutineContext Executing Thread
 */
class PostActionCreator(
    private val dispatcher: Dispatcher,
    private val postRepository: PostRepository,
    private val userRepository: UserRepository,
    override val coroutineContext: CoroutineContext = Dispatchers.IO
) : ActionCreator, CoroutineScope {

    /**
     * Fetch all posts
     *
     * @return List of post
     */
    suspend fun fetchPosts() = withContext(coroutineContext) {
        Result.runCatching {
            val postEntries = postRepository.fetchPosts()
            val postsDeferred = postEntries.map { post ->
                async {
                    val user = userRepository.fetchUser(post.userId).run {
                        User(id, name, username, email)
                    }
                    Post(post.id, post.title, post.body, user)
                }
            }
            postsDeferred.awaitAll()
        }.run {
            val action = PostAction.FetchedPosts(this)
            dispatcher.dispatch(action)
        }
    }

    /**
     * Fetch post with id
     *
     * @param id ID of post
     * @return Post
     */
    suspend fun fetchPost(id: Int) = withContext(coroutineContext) {
        Result.runCatching {
            postRepository.fetchPost(id).run {
                val user = userRepository.fetchUser(userId).run {
                    User(id, name, username, email)
                }
                Post(id, title, body, user)
            }
        }.run {
            val action = PostAction.FetchedPost(this)
            dispatcher.dispatch(action)
        }
    }

    /**
     * Submit post with parameters
     *
     * @param title title of post
     * @param body body of body
     * @param userId user id of submitting post
     */
    suspend fun submitPost(title: String, body: String, userId: Int) =
        withContext(coroutineContext) {
            Result.runCatching {
                // Submit
                postRepository.submitPost(
                    mapOf(
                        "title" to title,
                        "body" to body,
                        "userId" to userId
                    )
                )
                // Re-fetch
                fetchPosts()
            }.run {
                val action = PostAction.SubmittedPost(this)
                dispatcher.dispatch(action)
            }
        }

    /**
     * Update post with parameters
     *
     * @param id id of post
     * @param title title of post
     * @param body body of post
     */
    suspend fun updatePost(id: Int, title: String, body: String) = withContext(coroutineContext) {
        Result.runCatching {
            // Update
            postRepository.updatePost(
                id, mapOf(
                    "title" to title,
                    "body" to body
                )
            )
            // Re-fetch
            fetchPosts()
        }.run {
            val action = PostAction.UpdatedPost(this)
            dispatcher.dispatch(action)
        }
    }

    /**
     * Delete post with id
     *
     * @param id id of post
     */
    suspend fun deletePost(id: Int) = withContext(coroutineContext) {
        Result.runCatching {
            // Delete
            postRepository.deletePost(id)
            // Re-fetch
            fetchPosts()
        }.run {
            val action = PostAction.DeletedPost(this)
            dispatcher.dispatch(action)
        }
    }
}