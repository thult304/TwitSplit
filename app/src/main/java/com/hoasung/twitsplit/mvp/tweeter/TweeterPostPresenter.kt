package com.hoasung.twitsplit.mvp.tweeter

import com.hoasung.twitsplit.mvp.BaseMvpPresenter
import com.nvg.mvp.MvpView
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable

class TweeterPostPresenter : BaseMvpPresenter<MvpView>() {
    private val MAX_CHARS_ON_SEGMENT = 50
    private val postMessageList = arrayListOf<String>()
    private var mSplitDisposable: Disposable? = null
    private var mPostDisposable: Disposable? = null

    fun postMessages(message: String) {
        val view = mvpView as TweeterPostMvpView
        view.showLoading()

        mSplitDisposable = Flowable.fromCallable {
            SplitMessageUtil.splitMessage(message, MAX_CHARS_ON_SEGMENT)

        }.subscribeOn(view.getSubscribeScheduler())
                .observeOn(view.getObserveScheduler())
                .subscribe(
                        { segments -> onParsedMessageSuccess(segments) },
                        { error -> onParsedMessageFail(error) })
    }

    private fun onParsedMessageFail(error: Throwable) {
        val view = mvpView as TweeterPostMvpView

        view.onPostPartMessageFail(error)

        view.hideLoading()
    }

    private fun onParsedMessageSuccess(segments: List<String>) {
        val view = mvpView as TweeterPostMvpView

        postMessageList.clear()
        postMessageList.addAll(segments)

        mPostDisposable = Flowable.just(postMessageList)
                .takeWhile { postMessageList.size > 0 }
                .map { postMessageList.removeAt(0) }
                .flatMap { message -> postMessage(message) }
                .repeatUntil { postMessageList.isEmpty() }
                .subscribeOn(view.getSubscribeScheduler())
                .observeOn(view.getObserveScheduler())
                .subscribe(
                        {
                            view.onPostPartMessageSuccess(it)
                        },
                        {
                            onPostPartMessageFail(it)
                        },
                        {
                            onPostAllMessageSuccess()
                        })
    }

    private fun onPostPartMessageFail(error: Throwable) {
        val view = mvpView as TweeterPostMvpView

        view.hideLoading()
        view.onPostPartMessageFail(error)
    }

    private fun onPostAllMessageSuccess() {
        val view = mvpView as TweeterPostMvpView

        view.hideLoading()
        view.onPostAllMessageSuccess()
    }

    private fun postMessage(message: String): Flowable<String> {
        return Flowable.fromCallable({
            System.out.println("Posting message:$message")
            Thread.sleep(1000)
            message
        })
    }

    override fun detachView() {
        mSplitDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        mPostDisposable?.let {
            if (!it.isDisposed) {
                it.dispose()
            }
        }

        super.detachView()
    }
}