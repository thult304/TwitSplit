package com.hoasung.twitsplit.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.databinding.ViewPostedItemBinding
import com.hoasung.twitsplit.model.Post

class PostedItemViewHolder(val binding: ViewPostedItemBinding) : RecyclerView.ViewHolder(binding.getRoot())

class PostAdapter : RecyclerView.Adapter<PostedItemViewHolder>() {
    internal var mPostList: List<Post>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostedItemViewHolder {
        val binding: ViewPostedItemBinding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.view_posted_item,
                        parent, false)
        return PostedItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return if (mPostList == null) 0 else mPostList!!.size
    }

    override fun onBindViewHolder(holder: PostedItemViewHolder, position: Int) {
        holder.binding.post = mPostList!![position]
        holder.binding.executePendingBindings()
    }

    fun setPostList(posts: List<Post>) {
        if (mPostList == null) {
            mPostList = posts
            notifyItemRangeInserted(0, posts.size)
        } else {
            mPostList = posts
            notifyDataSetChanged()
        }
    }
}