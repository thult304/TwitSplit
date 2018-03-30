package com.nvg.mvp

/**
 * Every presenter in the app must either implement this interface or extend BaseMvpPresenter
 * indicating the MvpView type that wants to be attached with.
 *
 * Created by thult304 on 3/3/18.
 *
 */
interface MvpPresenter<V : MvpView> {

    fun attachView(mvpView: V)

    fun detachView()
}
