package com.hoasung.twitsplit.fragment.tweeter

import android.app.Activity
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.activity.MainActivity
import com.hoasung.twitsplit.databinding.FragmentTweeterPostBinding
import com.hoasung.twitsplit.fragment.BaseRootViewMvpFragment
import com.hoasung.twitsplit.listener.PostMessageListener
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostMvpView
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostPresenter
import com.nvg.mvp.MvpView

class PostMessageFragment :
        BaseRootViewMvpFragment<FragmentTweeterPostBinding, TweeterPostPresenter>(),
        TweeterPostMvpView,
        PostMessageListener {

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
    }

    override fun onClickPost(view: View) {
        mPresenter.splitMessage(mMessage!!)
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
}