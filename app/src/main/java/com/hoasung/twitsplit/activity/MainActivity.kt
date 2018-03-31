package com.hoasung.twitsplit.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.hoasung.twitsplit.R
import com.hoasung.twitsplit.fragment.BaseFragment

class MainActivity : BaseActivity() {
    companion object {
        private val EXTRA_FRAGMENT_CLASS_NAME = "fragment_class_name"
        private val EXTRA_FRAGMENT_BUNDLE_ARGS = "fragment_bundle_args"
        private val EXTRA_CAN_BACK_NAME = "ex_can_back"

        fun showFragment(activity: BaseActivity, fragmentClass: Class<out BaseFragment>,
                         data: Bundle? = null, canBack: Boolean = false, requestCode: Int = -1, flag: Int = -1) {
            val intent = Intent(activity, getActivityClass())
            if (flag != -1) {
                intent.addFlags(flag)
            }
            intent.putExtra(EXTRA_FRAGMENT_CLASS_NAME, fragmentClass.name)
            if (data != null) {
                intent.putExtra(EXTRA_FRAGMENT_BUNDLE_ARGS, data)
            }

            intent.putExtra(EXTRA_CAN_BACK_NAME, canBack)

            if (requestCode != -1) {
                activity.startActivityForResult(intent, requestCode)
            } else {
                activity.startActivity(intent)
            }


        }

        private fun getActivityClass(): Class<*> {
            return MainActivity::class.java
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        onInitWithIntent(intent)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        onInitWithIntent(intent)
    }

    private fun onInitWithIntent(intent: Intent?) {
        if (intent != null) {
            val bundle = intent.getBundleExtra(EXTRA_FRAGMENT_BUNDLE_ARGS)
            replaceFragment(R.id.fragment_container, getOpenFragmentClass(intent), bundle, getCanBeBackOfOpenFragmentClass(intent))
        }
    }

    private fun getCanBeBackOfOpenFragmentClass(intent: Intent?): Boolean {
        return intent?.getBooleanExtra(EXTRA_CAN_BACK_NAME, false) ?: false
    }

    /**
     * Replace fragment with back stack support
     *
     * @param containerId
     * @param fragmentClassName
     * @param data
     * @param isAddToBackStack
     */
    private fun replaceFragment(containerId: Int, fragmentClassName: String?, data: Bundle?,
                                isAddToBackStack: Boolean) {
        if (fragmentClassName == null) {
            throw NullPointerException("Fragment cannot be null")
        }
        val fragmentManager = supportFragmentManager
        var fragment: Fragment? = fragmentManager.findFragmentByTag(fragmentClassName)

        if (fragment == null) {
            fragment = Fragment.instantiate(this, fragmentClassName)
        }

        if (fragment != null) {
            data?.let {
                if (fragment.arguments != null) {
                    fragment.arguments?.putAll(it)
                } else {
                    fragment.arguments = it
                }
            }

            attachFragment(containerId, fragment, isAddToBackStack, fragmentClassName)

        } else {
            throw NullPointerException("could not instance fragment $fragmentClassName")
        }
    }


    private fun attachFragment(layout: Int, fragment: Fragment, isAddToBackStack: Boolean, tag: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(layout, fragment, tag)
        if (isAddToBackStack) {
            fragmentTransaction.addToBackStack(null)
        }
        fragmentTransaction.commitAllowingStateLoss()
    }

    private fun getOpenFragmentClass(intent: Intent?): String? {
        return if (intent != null) {
            intent.getStringExtra(EXTRA_FRAGMENT_CLASS_NAME)
        } else null
    }
}
