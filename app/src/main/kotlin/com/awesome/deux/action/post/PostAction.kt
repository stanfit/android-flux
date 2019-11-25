package com.awesome.deux.action.post

import com.awesome.deux.flux.Action
import com.awesome.deux.flux.Result
import com.awesome.deux.model.Post

/**
 * Post's Action
 */
sealed class PostAction : Action {

    /**
     * This is action that fetched posts.
     *
     * @property result Result of posts
     */
    data class FetchedPosts(val result: Result<List<Post>>) : PostAction()

    /**
     * This is action that fetched post.
     *
     * @property result Result of Post
     */
    data class FetchedPost(val result: Result<Post>) : PostAction()

    /**
     * This is action that submitted post.
     *
     * @property result Result
     */
    data class SubmittedPost(private val result: Result<Unit>) : PostAction()

    /**
     * This is action that updated post.
     *
     * @property result Result
     */
    data class UpdatedPost(private val result: Result<Unit>) : PostAction()

    /**
     * This is action that deleted post.
     *
     * @property result Result
     */
    data class DeletedPost(private val result: Result<Unit>) : PostAction()
}