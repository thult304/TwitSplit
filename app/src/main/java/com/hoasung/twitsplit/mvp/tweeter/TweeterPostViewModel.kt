package com.hoasung.twitsplit.mvp.tweeter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class TweeterPostViewModel : ViewModel() {
    private val MAX_CHARS_ON_SEGMENT = 50

    private var postedMessages: MutableLiveData<List<String>>? = null
    private val disposable = CompositeDisposable()

    private var postedErrors: MutableLiveData<Throwable>? = null

    private var postingStatus: MutableLiveData<PostingStatus>? = null

    private val postMessageList = arrayListOf<String>()

    fun getPostingStatus(): LiveData<PostingStatus>? {
        if (postingStatus == null) {
            postingStatus = MutableLiveData<PostingStatus>()
        }

        return postingStatus;
    }

    fun getPostedErrors(): LiveData<Throwable> {
        if (postedErrors == null) {
            postedErrors = MutableLiveData<Throwable>()
        }

        return postedErrors!!
    }


    fun getPostedMessages(): LiveData<List<String>> {
        if (postedMessages == null) {
            postedMessages = MutableLiveData<List<String>>()

            loadPostedMessages()
        }

        return postedMessages!!
    }

    private fun loadPostedMessages() {
        //we can load postedMessages from local database.
        //but in this simple demo, we just keep data in the memory so we ignore this function
    }

    fun postMessages(message: String) {

        postingStatus?.postValue(PostingStatus.Posting)

        disposable.add(Flowable.fromCallable {
            SplitMessageUtil.splitMessage(message, MAX_CHARS_ON_SEGMENT)

        }.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        { segments -> onParsedMessageSuccess(segments) },
                        { error -> onParsedMessageFail(error) })
        )
    }

    private fun onParsedMessageFail(error: Throwable) {
        postingStatus?.postValue(PostingStatus.FinishedFail)
        postedErrors?.postValue(error)

    }

    private fun onParsedMessageSuccess(segments: List<String>) {
        postMessageList.clear()
        postMessageList.addAll(segments)

        disposable.add(Flowable.just(postMessageList)
                .takeWhile { postMessageList.size > 0 }
                .map { postMessageList.removeAt(0) }
                .flatMap { message -> postMessage(message) }
                .repeatUntil { postMessageList.isEmpty() }
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(
                        {
                            onPostPartMessageSuccess(it)
                        },
                        {
                            onPostPartMessageFail(it)
                        },
                        {
                            onPostAllMessageSuccess()
                        })
        )
    }

    private fun onPostAllMessageSuccess() {
        postingStatus?.postValue(PostingStatus.FinishedSuccess)
    }

    private fun onPostPartMessageSuccess(postedMsg: String) {
        var histories: ArrayList<String>? = postedMessages?.value as ArrayList<String>?;
        if (histories == null) {
            histories = ArrayList()
        }
        histories.add(postedMsg)

        postedMessages?.postValue(histories)
    }

    private fun postMessage(message: String): Flowable<String> {
        return Flowable.fromCallable({
            System.out.println("Posting message:$message")
            Thread.sleep(1000)
            message
        })
    }

    private fun onPostPartMessageFail(error: Throwable) {
        postingStatus?.postValue(PostingStatus.FinishedFail)
        postedErrors?.postValue(error)
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }

    fun resetPostingStatus() {
        postingStatus?.postValue(PostingStatus.Init)
    }

    fun clearError() {
        postedErrors?.postValue(null)
    }
}