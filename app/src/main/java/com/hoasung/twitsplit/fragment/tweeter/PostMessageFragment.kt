package com.hoasung.twitsplit.fragment.tweeter

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.ScrollingMovementMethod
import android.view.View
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.activity.MainActivity
import com.hoasung.twitsplit.databinding.FragmentTweeterPostBinding
import com.hoasung.twitsplit.fragment.BaseRootViewMvpFragment
import com.hoasung.twitsplit.listener.PostMessageListener
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostMvpView
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostPresenter
import com.nvg.mvp.MvpView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PostMessageFragment :
        BaseRootViewMvpFragment<FragmentTweeterPostBinding, TweeterPostPresenter>(),
        TweeterPostMvpView,
        PostMessageListener {

    override fun getSubscribeScheduler(): Scheduler? {
        return Schedulers.io()
    }

    override fun getObserveScheduler(): Scheduler? {
        return AndroidSchedulers.mainThread()
    }

    companion object {
        fun showMe(activity: Activity) {
            MainActivity.showFragment(activity, PostMessageFragment::class.java)
        }
    }

    private var mMessage: String? = null

    override var mvpView: MvpView = this

    override fun createPresenter(): TweeterPostPresenter {
        return TweeterPostPresenter()
    }

    override val layoutId = R.layout.fragment_tweeter_post

    override fun onViewBindingCreated(viewBinding: FragmentTweeterPostBinding) {
        viewBinding.errorMessageView.visibility = View.INVISIBLE
        viewBinding.handlers = this

        viewBinding.messageBox.addTextChangedListener(TextChangedListener())

        viewBinding.postedMessageBox.movementMethod = ScrollingMovementMethod();

    }

    override fun onClickPost(view: View) {
        mPresenter.postMessages(mMessage!!)
    }

    private fun updateUIForPostButton() {
        viewBinding.postButton.isEnabled = !TextUtils.isEmpty(mMessage)
    }

    inner class TextChangedListener : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            mMessage = viewBinding.messageBox.text.toString().trim()
            updateUIForPostButton()
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    override fun onPostPartMessageSuccess(postedMessage: String) {
        val builder = StringBuilder()
        builder.append(viewBinding.postedMessageBox.text)
        builder.append(postedMessage)
        builder.append("\n")
        viewBinding.postedMessageBox.text = builder.toString()
    }

    override fun onPostPartMessageFail(error: Throwable) {
        viewBinding.errorMessageView.visibility = View.VISIBLE
        showErrorDialog(error.message ?: getString(R.string.error_message_something_went_wrong))
    }

    override fun onPostAllMessageSuccess() {
        viewBinding.messageBox.text.clear()
        viewBinding.errorMessageView.visibility = View.INVISIBLE
    }
}