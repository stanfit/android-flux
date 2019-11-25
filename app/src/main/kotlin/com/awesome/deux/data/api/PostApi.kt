package com.awesome.deux.data.api

import com.awesome.deux.data.entity.PostEntity
import retrofit2.http.*

/**
 * Post's API for Retrofit
 */
interface PostApi {

    /**
     * Fetch all posts
     *
     * @return List of post
     */
    @GET("posts")
    suspend fun fetchPosts(): List<PostEntity>

    /**
     * Fetch post by id
     *
     * @param id ID of post
     * @return Post
     */
    @GET("posts/{id}")
    suspend fun fetchPost(@Path("id") id: Int): PostEntity

    /**
     * Submit post with parameters
     *
     * @param request request parameters
     * @return Post
     */
    @FormUrlEncoded
    @JvmSuppressWildcards
    @POST("posts")
    suspend fun submitPost(@FieldMap request: Map<String, Any>): PostEntity

    /**
     * Update post with id and parameters
     *
     * @param id id of post
     * @param request request parameters
     * @return Post
     */
    @FormUrlEncoded
    @JvmSuppressWildcards
    @PATCH("posts/{id}")
    suspend fun updatePost(@Path("id") id: Int, @FieldMap request: Map<String, Any>): PostEntity

    /**
     * Delete post with id
     *
     * @param id id of post
     * @return Post
     */
    @DELETE("posts/{id}")
    suspend fun deletePost(@Path("id") id: Int)
}