package com.hoasung.twitsplit.fragment

import android.databinding.ViewDataBinding
import android.os.Bundle
import com.hoasung.twitsplit.mvp.BaseMvpPresenter
import com.nvg.mvp.MvpView

/**
 * Created by thult304 on 3/3/18.
 */

abstract class BaseRootViewMvpFragment<ViewBinding : ViewDataBinding, Presenter : BaseMvpPresenter<MvpView>> : BaseRootViewFragment<ViewBinding>() {
    protected lateinit var mPresenter: Presenter

    protected abstract var mvpView: MvpView

    override fun onCreate(savedInstanceState: Bundle?) {
        if (!::mPresenter.isInitialized) {
            mPresenter = createPresenter()
        }

        mPresenter.attachView(mvpView)

        super.onCreate(savedInstanceState)
    }

    override fun onDestroy() {
        mPresenter.detachView()
        super.onDestroy()
    }

    protected abstract fun createPresenter(): Presenter
}
