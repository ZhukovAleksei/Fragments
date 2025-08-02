package com.example.newmedia.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.newmedia.R
import com.example.newmedia.databinding.CardPostBinding
import com.example.newmedia.dto.Post
import com.example.newmedia.dto.formatNumber


interface OnInteractionListener {
    fun onLike(post: Post)
    fun onShare(post: Post)
    fun onRemove(post: Post)
    fun onEdit(post: Post)
}

class PostsAdapter( private val onInteractionListener: OnInteractionListener) : ListAdapter<Post, PostViewHolder>(PostDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = CardPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(view, onInteractionListener)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}



class PostViewHolder(private val binding: CardPostBinding, private val onInteractionListener: OnInteractionListener) : RecyclerView.ViewHolder(binding.root){

    fun bind(post: Post) = with(binding){

        infoAuthor.text = post.author
        data.text = post.published
        content.text = post.content
        like.text = formatNumber(post.countLikes.toInt())                    //=======//
        share.text = formatNumber(post.countShare.toInt())
        countViews.text = formatNumber(post.countViews.toInt())

        like.isChecked = post.likedByMe                                      //=======//

        like.setOnClickListener {
            onInteractionListener.onLike(post)
        }
        share.setOnClickListener {
            onInteractionListener.onShare(post)
        }
        buttonMenu.setOnClickListener {
            PopupMenu(it.context, it).apply {
                inflate(R.menu.menu)
                setOnMenuItemClickListener { item ->
                    when(item.itemId) {
                        R.id.remove -> {
                            onInteractionListener.onRemove(post)
                            true
                        }
                        R.id.change -> {
                            onInteractionListener.onEdit(post)
                            true
                        }
                        else -> false
                    }
                }
            }.show()
        }
    }
}

object PostDiffCallback: DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Post, newItem: Post) = oldItem == newItem

}