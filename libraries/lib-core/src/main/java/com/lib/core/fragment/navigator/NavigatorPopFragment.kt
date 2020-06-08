package com.lib.core.fragment.navigator

import android.util.Log
import androidx.fragment.app.Fragment
import com.lib.core.R
import com.lib.core.activity.listener.OnActivityBackPressedListener
import com.lib.core.fragment.BaseFragment
import com.lib.core.navigator.FragmentNavigator

abstract class NavigatorPopFragment : BaseFragment(),
    NavigatorFragmentContract.View,
    OnActivityBackPressedListener,
    FragmentNavigator.OnPopFragmentListener {

    private val mFragmentNavigator: FragmentNavigator by lazy {
        FragmentNavigator(childFragmentManager)
    }

    override
    fun getNavigator(): FragmentNavigator {
        return mFragmentNavigator
    }

    override
    fun getCurrentFragment(): Fragment? {
        return getNavigator().getCurrentFragment()
    }

    override
    fun pushFragment(fragment: Fragment, tag: String, anim: Boolean) {
        if (anim) {
            pushFragment(fragment, tag, Pair(R.anim.enter_from_right, R.anim.exit_to_left))
        } else {
            pushFragment(fragment, tag, null)
        }
    }

    override
    fun pushFragment(fragment: Fragment, tag: String, anim: Pair<Int, Int>?) {
        getNavigator().pushFragment(getContainerId(), fragment, tag, anim)
    }

    override
    fun popFragment(anim: Boolean) {
        if (anim) {
            popFragment(Pair(R.anim.enter_from_left, R.anim.exit_to_right))
        }else{
            popFragment(null)
        }
    }

    override
    fun popFragment(anim: Pair<Int, Int>?) {
        getNavigator().popFragment(anim)
    }

    override
    fun onPopLastFragment(navigator: FragmentNavigator, lastFragment: Fragment?) {
        Log.d(TAG, String.format("onPopLastFragment: %s, lastFragment => %s", this, lastFragment))
        getNavigatorActivity()?.popFragment(false)
    }

    override
    fun onHandleBackPressed(): Boolean {
        Log.d(TAG, String.format("onHandleBackPressed: %s", this))
        val currentFragment: Fragment? = getCurrentFragment()
        return if (currentFragment is OnActivityBackPressedListener) {
            if (!currentFragment.onHandleBackPressed()) {
                popFragment(true)
            }
            true
        } else {
            false
        }
    }
}