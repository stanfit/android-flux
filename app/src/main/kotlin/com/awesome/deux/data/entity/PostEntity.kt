package com.awesome.deux.data.entity

import kotlinx.serialization.Serializable

/**
 * Post API response class.
 *
 * @property id ID
 * @property title Title
 * @property body Content
 * @property userId User's ID
 */
@Serializable
data class PostEntity(
    val id: Int,
    val title: String,
    val body: String,
    val userId: Int
)