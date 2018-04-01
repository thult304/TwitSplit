package com.hoasung.twitsplit.repository

import com.hoasung.twitsplit.model.Post
import io.reactivex.Flowable

class TweeterPostRepository : PostRepository {

    /**
     * In a real app, this function should be setup by tweeter sdk's api
     * in this project we just simple setup from fromCallable with  a sleep time about 1 minute for demo purpose
     *
     */
    override fun postMessage(message: String): Flowable<Post> {
        return Flowable.fromCallable({
            System.out.println("Posting message:$message")
            Thread.sleep(1000)
            Post(System.currentTimeMillis(), message)
        })
    }
}