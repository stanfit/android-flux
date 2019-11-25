package com.awesome.deux.ext

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.xwray.groupie.Group
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder

/**
 * Groupie Adapter.
 */
val groupAdapter get() = GroupAdapter<GroupieViewHolder>()

/**
 * Groupie Adapter of RecyclerView.
 */
val RecyclerView.groupAdapter get() = (adapter as? GroupAdapter)

/**
 * Groupie Adapter item click listener.
 *
 * @param T Item
 * @param onItemClickListener Listener
 */
inline fun <reified T : Group> GroupAdapter<GroupieViewHolder>.setGroupOnItemClickListener(
    crossinline onItemClickListener: (T, View) -> Unit
) {
    setOnItemClickListener { item, view ->
        if (item is T) onItemClickListener(item, view)
    }
}

/**
 * Groupie Adapter item long click listener.
 *
 * @param T Item
 * @param onItemLongClickListener Listener
 */
inline fun <reified T : Group> GroupAdapter<GroupieViewHolder>.setGroupOnItemLongClickListener(
    crossinline onItemLongClickListener: (T, View) -> Boolean
) {
    setOnItemLongClickListener { item, view ->
        return@setOnItemLongClickListener if (item is T) onItemLongClickListener(
            item,
            view
        ) else false
    }
}
