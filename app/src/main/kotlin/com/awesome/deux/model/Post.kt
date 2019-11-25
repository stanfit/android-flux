package com.awesome.deux.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Post data class
 *
 * @property id ID
 * @property title Title
 * @property body Content
 * @property user [User]
 */
@Parcelize
data class Post(
    val id: Int,
    val title: String,
    val body: String,
    val user: User
) : Parcelable