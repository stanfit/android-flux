package com.awesome.deux.ui.post

import com.awesome.deux.R
import com.awesome.deux.model.Post
import com.xwray.groupie.kotlinandroidextensions.GroupieViewHolder
import com.xwray.groupie.kotlinandroidextensions.Item
import kotlinx.android.synthetic.main.item_post.view.*

/**
 * RecyclerView Item of Post.
 *
 * @property post Post
 */
class PostItem(val post: Post) : Item() {

    override fun getId(): Long = post.id.toLong()

    override fun getLayout(): Int = R.layout.item_post

    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
        viewHolder.itemView.title.text = post.title
        viewHolder.itemView.body.text = post.body
        viewHolder.itemView.username.text = post.user.username
    }

    override fun isSameAs(other: com.xwray.groupie.Item<*>?): Boolean {
        if (other is PostItem) {
            return post.toString() == other.post.toString()
        }
        return false
    }

    companion object {

        /**
         * Convert to items from posts.
         *
         * @param posts
         * @return items
         */
        fun from(posts: List<Post>): List<PostItem> = posts.map { PostItem(it) }
    }
}
