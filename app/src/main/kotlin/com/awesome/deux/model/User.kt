package com.awesome.deux.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * User
 *
 * @property id ID
 * @property name Name
 * @property username User name
 * @property email Email
 */
@Parcelize
data class User(
    val id: Int,
    val name: String,
    val username: String,
    val email: String
) : Parcelable