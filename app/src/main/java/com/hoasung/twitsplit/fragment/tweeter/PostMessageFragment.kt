package com.hoasung.twitsplit.fragment.tweeter

import android.app.Activity
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.activity.MainActivity
import com.hoasung.twitsplit.databinding.FragmentTweeterPostBinding
import com.hoasung.twitsplit.fragment.BaseRootViewFragment
import com.hoasung.twitsplit.fragment.BaseRootViewMvpFragment
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostMvpView
import com.hoasung.twitsplit.mvp.tweeter.TweeterPostPresenter
import com.nvg.mvp.MvpView

class PostMessageFragment :
        BaseRootViewMvpFragment<FragmentTweeterPostBinding,
                TweeterPostPresenter>(), TweeterPostMvpView {
    override var mvpView: MvpView = this

    override fun createPresenter(): TweeterPostPresenter {
        return TweeterPostPresenter()
    }

    override val layoutId = R.layout.fragment_tweeter_post

    override fun onViewBindingCreated(viewBinding: FragmentTweeterPostBinding) {
    }

    companion object {
        fun showMe(activity: Activity) {
            MainActivity.showFragment(activity, PostMessageFragment::class.java)
        }
    }
}