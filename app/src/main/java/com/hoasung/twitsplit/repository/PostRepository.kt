package com.hoasung.twitsplit.repository

import com.hoasung.twitsplit.model.Post
import io.reactivex.Flowable

interface PostRepository {
    fun postMessage(message: String): Flowable<Post>
}