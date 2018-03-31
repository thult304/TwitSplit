package com.hoasung.twitsplit.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import com.hoasung.twitsplit.widget.CustomProgressBar

/**
 * Created by thu.le on 5/13/2015.
 */
class DialogUtil {
    companion object {
        fun getProgressDialog(context: Context, cancelListener: DialogInterface.OnCancelListener): Dialog {
            return CustomProgressBar(context, cancelListener)
        }
    }
}
