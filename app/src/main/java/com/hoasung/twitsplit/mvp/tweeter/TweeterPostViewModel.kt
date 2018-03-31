package com.hoasung.twitsplit.mvp.tweeter

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel


class TweeterPostViewModel : ViewModel() {
    private var postedMessages: MutableLiveData<List<String>>? = null

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

}