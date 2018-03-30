package com.hoasung.twitsplit.fragment

import android.app.Fragment

/**
 * Created by thult304 on 3/3/18.
 */
open class BaseFragment : Fragment() {

    fun isFragmentValid(): Boolean {
        return activity != null && !isDetached
    }

    protected fun showLoading() {
    }


    protected fun hideLoading() {
    }

    protected fun closeMe() {
        activity?.onBackPressed()
    }
}