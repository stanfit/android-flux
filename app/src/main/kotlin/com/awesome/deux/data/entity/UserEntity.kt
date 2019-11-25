package com.awesome.deux.data.entity

import kotlinx.serialization.Serializable

/**
 * User API Response class.
 *
 * @property id ID
 * @property name Name
 * @property username User name
 * @property email Email
 */
@Serializable
data class UserEntity(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
)