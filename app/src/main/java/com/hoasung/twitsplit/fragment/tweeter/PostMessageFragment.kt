package com.hoasung.twitsplit.fragment.tweeter

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.activity.BaseActivity
import com.hoasung.twitsplit.activity.MainActivity
import com.hoasung.twitsplit.adapter.PostAdapter
import com.hoasung.twitsplit.databinding.FragmentTweeterPostBinding
import com.hoasung.twitsplit.fragment.BaseRootViewFragment
import com.hoasung.twitsplit.listener.PostMessageListener
import com.hoasung.twitsplit.model.Post
import com.hoasung.twitsplit.mvvm.tweeter.PostingStatus
import com.hoasung.twitsplit.mvvm.tweeter.TweeterPostViewModel


class PostMessageFragment :
        BaseRootViewFragment<FragmentTweeterPostBinding>(),
        PostMessageListener {

    companion object {
        fun showMe(activity: BaseActivity) {
            MainActivity.showFragment(activity, PostMessageFragment::class.java)
        }
    }

    private lateinit var postViewModel: TweeterPostViewModel

    private var mMessage: String? = null

    private lateinit var mPostAdapter: PostAdapter

    override val layoutId = R.layout.fragment_tweeter_post


    override fun onViewBindingCreated(viewBinding: FragmentTweeterPostBinding) {
        viewBinding.errorMessageView.visibility = View.INVISIBLE
        viewBinding.handlers = this

        viewBinding.messageBox.addTextChangedListener(TextChangedListener())

        mPostAdapter = PostAdapter()
        viewBinding.postsList.adapter = mPostAdapter

        postViewModel = ViewModelProviders.of(this).get(TweeterPostViewModel::class.java)

        postViewModel.getPostedMessages().observe(this, Observer<List<Post>> {
            if (it != null) {
                mPostAdapter.setPostList(it)
            }

            viewBinding.executePendingBindings()
        })

        postViewModel.getPostingStatus()?.observe(this, Observer { posting ->
            when (posting) {
                PostingStatus.Init -> {
                    //do nothing
                }
                PostingStatus.Posting -> {
                    showLoading()
                }
                PostingStatus.FinishedFail -> {
                    hideLoading()
                    postViewModel.resetPostingStatus()
                }
                PostingStatus.FinishedSuccess -> {
                    hideLoading()
                    viewBinding.messageBox.text.clear()
                    viewBinding.errorMessageView.visibility = View.INVISIBLE
                    postViewModel.resetPostingStatus()
                }
            }
        })

        postViewModel.getPostedErrors().observe(this, Observer { error ->

            error?.let {
                viewBinding.errorMessageView.visibility = View.VISIBLE
                showErrorDialog(error.message
                        ?: getString(R.string.error_message_something_went_wrong))

                postViewModel?.clearError()
            }

        })
    }

    override fun onClickPost(view: View) {
        postViewModel.postMessages(mMessage!!)
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