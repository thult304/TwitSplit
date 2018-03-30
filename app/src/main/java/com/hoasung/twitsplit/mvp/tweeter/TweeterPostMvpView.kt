package com.hoasung.twitsplit.mvp.tweeter

import com.nvg.mvp.MvpView
import io.reactivex.Scheduler

interface TweeterPostMvpView : MvpView{
    fun showLoading()
    fun hideLoading()
    fun getSubscribeScheduler(): Scheduler?
    fun getObserveScheduler(): Scheduler?
    fun onPostPartMessageSuccess(postedMessage: String)
    fun onPostPartMessageFail(error: Throwable)
    fun onPostAllMessageSuccess()
}