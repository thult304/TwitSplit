package com.hoasung.twitsplit.fragment

import android.app.Fragment
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by thult304 on 3/3/18.
 */

abstract class BaseRootViewFragment<ViewBinding : ViewDataBinding> : BaseFragment() {
    protected lateinit var viewBinding: ViewBinding

    protected abstract val layoutId: Int


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (!::viewBinding.isInitialized) {
            viewBinding = DataBindingUtil.inflate(inflater!!, layoutId, null, false)
            onViewBindingCreated(viewBinding)
        } else {
            onViewBindingCreated(viewBinding)
        }

        val parentView = viewBinding.root.parent as ViewGroup?

        parentView?.removeView(viewBinding.root)

        return viewBinding.root
    }

    protected abstract fun onViewBindingCreated(viewBinding: ViewBinding)
}
