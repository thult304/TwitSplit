package com.hoasung.twitsplit.fragment

import android.app.Dialog
import android.app.Fragment
import android.content.DialogInterface
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.util.DialogUtil

/**
 * Created by thult304 on 3/3/18.
 */
open class BaseFragment : Fragment() {
    private var mProgressBar: Dialog? = null
    private var mOnCancelProgressListener: DialogInterface.OnCancelListener? = null

    fun isFragmentValid(): Boolean {
        return activity != null && !isDetached
    }

    fun showLoading() {
        toggleProgressLoading(true)
    }


    fun hideLoading() {
        toggleProgressLoading(false)
    }

    protected fun closeMe() {
        activity?.onBackPressed()
    }

    private val cancelProgressDialog: DialogInterface.OnCancelListener
        get() = DialogInterface.OnCancelListener { onDestroyRequest() }

    protected fun onDestroyRequest() {
    }

    /**
     * Show progress bar loading on screen
     */
    fun toggleProgressLoading(isShow: Boolean) {
        if (mProgressBar == null) {
            mOnCancelProgressListener = cancelProgressDialog
            mProgressBar = DialogUtil.getProgressDialog(activity, mOnCancelProgressListener!!)
        }

        if (isShow) {
            if (mOnCancelProgressListener != null) {
                mProgressBar?.setOnCancelListener(mOnCancelProgressListener)
            } else {
                mProgressBar?.setCancelable(false)
            }
            if (!(mProgressBar?.isShowing == true)) {
                mProgressBar?.show()
            }
        } else {
            mProgressBar?.dismiss()
        }
    }

    fun showErrorDialog(message: String) {

        DialogUtil.showOneButtonDialog(activity, getString(R.string.dialog_error_title),
                message, null)
    }
}