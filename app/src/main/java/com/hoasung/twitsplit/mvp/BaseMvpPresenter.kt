package com.hoasung.twitsplit.mvp

import com.nvg.mvp.MvpPresenter
import com.nvg.mvp.MvpView

/**
 * Base class that implements the MvpPresenter interface and provides a base implementation for
 * attachView() and detachView(). It also handles keeping a reference to the mvpView that
 * can be accessed from the children classes by calling getMvpView().
 *
 * Created by thult304 on 3/3/18.
 *
 */
open class BaseMvpPresenter<T : MvpView> : MvpPresenter<T> {

    var mvpView: T? = null

    val isViewAttached: Boolean
        get() = mvpView != null

    override fun attachView(mvpView: T) {
        this.mvpView = mvpView
    }

    override fun detachView() {
        mvpView = null
    }

    fun checkViewAttached() {
        if (!isViewAttached) throw MvpViewNotAttachedException()
    }

    class MvpViewNotAttachedException : RuntimeException("Please call MvpPresenter.attachView(MvpView) before" + " requesting data to the MvpPresenter")
}

