package com.hoasung.twitsplit.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

import com.hoasung.twitsplit.R;

/**
 * Created by thu.le on 7/16/2015.
 */
public class CustomProgressBar extends Dialog {
    private OnCancelListener mCancelListener;

    private AnimationDrawable mRocketAnimation;

    public CustomProgressBar(Context context, OnCancelListener cancelListener) {
        super(context, R.style.DialogStyle);
        this.mCancelListener = cancelListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.view_progress_dialog_standard);

        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setCanceledOnTouchOutside(false);

        if (mCancelListener != null) {
            setOnCancelListener(mCancelListener);
        }
    }

    @Override
    public void show() {
        super.show();
        startOrStopAnimation(true);
    }


    private void startOrStopAnimation(boolean isStart) {
    }

    @Override
    public void onBackPressed() {
        startOrStopAnimation(false);

        super.onBackPressed();
    }

    @Override
    public void dismiss() {
        startOrStopAnimation(false);
        super.dismiss();
    }
}
