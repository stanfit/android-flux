package com.awesome.deux.data.repository

import com.awesome.deux.data.api.PostApi
import com.awesome.deux.data.entity.PostEntity

/**
 * Repository of Post
 *
 * @property post PostApi
 */
class PostRepository(private val post: PostApi) {

    /**
     * Cache of post
     */
    private val cache = mutableMapOf<Int, PostEntity>()

    /**
     * Fetch all posts
     *
     * @return List of post
     */
    suspend fun fetchPosts(): List<PostEntity> {
        return post.fetchPosts().onEach {
            cache[it.id] = it
        }
    }

    /**
     * Fetch post by id
     *
     * @param id ID of post
     * @return Post
     */
    suspend fun fetchPost(id: Int): PostEntity {
        return cache.getOrElse(id) { post.fetchPost(id) }
    }

    /**
     * Submit post with parameters
     *
     * @param request request parameters
     * @return Post
     */
    suspend fun submitPost(request: Map<String, Any>): PostEntity {
        return post.submitPost(request)
    }

    /**
     * Update post with id and parameters
     *
     * @param id id of post
     * @param request request parameters
     * @return Post
     */
    suspend fun updatePost(id: Int, request: Map<String, Any>): PostEntity {
        return post.updatePost(id, request)
    }

    /**
     * Delete post with id
     *
     * @param id id of post
     * @return Post
     */
    suspend fun deletePost(id: Int) {
        post.deletePost(id)
    }
}