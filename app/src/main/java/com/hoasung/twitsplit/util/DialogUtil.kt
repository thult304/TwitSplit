package com.hoasung.twitsplit.util

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.text.TextUtils
import com.afollestad.materialdialogs.MaterialDialog
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.widget.CustomProgressBar

/**
 * Created by thu.le on 5/13/2015.
 */
class DialogUtil {
    companion object {
        fun getProgressDialog(context: Context, cancelListener: DialogInterface.OnCancelListener): Dialog {
            return CustomProgressBar(context, cancelListener)
        }

        fun showOneButtonDialog(context: Context, title: String, content: String,
                                callback: MaterialDialog.SingleButtonCallback?) {
            val builder = getDialogBuilder(context)

            if (!TextUtils.isEmpty(title)) {
                builder.title(title)
            }
            if (!TextUtils.isEmpty(content)) {
                builder.content(content)
            }

            builder.positiveText(R.string.common_ok)

            if (callback != null) {
                builder.onPositive(callback)
            }

            builder.show()
        }

        private fun getDialogBuilder(context: Context): MaterialDialog.Builder {
            return MaterialDialog.Builder(context)
                    .cancelable(true)
                    .positiveColorRes(R.color.color_deliver)
                    .negativeColorRes(R.color.color_deliver)
                    .titleColorRes(R.color.color_deliver)
                    .widgetColorRes(R.color.color_deliver)
        }

    }
}
