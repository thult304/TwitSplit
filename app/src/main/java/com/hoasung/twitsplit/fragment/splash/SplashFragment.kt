package com.nvg.fragment.splash

import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.activity.BaseActivity
import com.hoasung.twitsplit.databinding.FragmentSplashBinding
import com.hoasung.twitsplit.fragment.BaseRootViewFragment
import com.hoasung.twitsplit.fragment.tweeter.PostMessageFragment
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by thult304 on 3/3/18.
 */
class SplashFragment : BaseRootViewFragment<FragmentSplashBinding>() {
    private val SPLASH_TIME: Long = 3
    private var subscription: Disposable? = null


    override val layoutId = R.layout.fragment_splash

    override fun onViewBindingCreated(viewBinding: FragmentSplashBinding) {
        handleGoingToFirstScreen()
    }

    private fun handleGoingToFirstScreen() {
        subscription = Observable.just(0).delay(SPLASH_TIME, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread()).subscribe {

            goToPostMessageScreen()
        }
    }

    private fun goToPostMessageScreen() {
        PostMessageFragment.showMe(activity as BaseActivity)

        closeMe()
    }

    override fun onDestroy() {
        super.onDestroy()

        subscription?.let {

            if (!it.isDisposed) {
                it.dispose()
            }
        }
    }
}
