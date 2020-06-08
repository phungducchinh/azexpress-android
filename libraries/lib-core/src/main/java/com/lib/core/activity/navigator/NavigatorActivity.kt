package com.lib.core.activity.navigator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.util.Log
import com.lib.core.R
import com.lib.core.activity.BaseActivity
import com.lib.core.activity.listener.OnActivityBackPressedListener
import com.lib.core.navigator.FragmentNavigator

abstract class NavigatorActivity : BaseActivity(),
    NavigatorActivityContract.View,
    FragmentNavigator.OnPopFragmentListener {

    companion object {
        const val FRAGMENT_TAG_LIST_KEY = "FRAGMENT_TAG_LIST_KEY"
    }

    private val mFragmentNavigator: FragmentNavigator by lazy {
        FragmentNavigator(supportFragmentManager)
    }

    override
    fun getNavigator(): FragmentNavigator {
        return mFragmentNavigator
    }

    override
    fun init(savedInstanceState: Bundle?) {
        super.init(savedInstanceState)
        savedInstanceState?.let {
            getNavigator().setTagList(it.getStringArrayList(FRAGMENT_TAG_LIST_KEY))
        }
        getNavigator().setOnPopLastFragmentListener(this)
    }

    override
    fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putStringArrayList(FRAGMENT_TAG_LIST_KEY, getNavigator().getTagList())
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
        } else {
            popFragment(null)
        }
    }

    override
    fun popFragment(anim: Pair<Int, Int>?) {
        getNavigator().popFragment(anim)
    }

    override
    fun showFragment(fragment: Fragment?) {
        getNavigator().showFragment(fragment)
    }

    override
    fun hideFragment(fragment: Fragment?) {
        getNavigator().hideFragment(fragment)
    }

    override
    fun onPopLastFragment(navigator: FragmentNavigator, lastFragment: Fragment?) {
        Log.d(TAG, String.format("onPopLastFragment: %s, lastFragment => %s", this, lastFragment))
        finish()
    }

    override
    fun onPopFragmentEmpty(navigator: FragmentNavigator) {
        finish()
    }


    override
    fun onBackPressed() {
        Log.d(TAG, String.format("onBackPressed: %s", this))
        this.mIsBackPressed = true
        val currentFragment: Fragment? = getCurrentFragment()
        if (currentFragment is OnActivityBackPressedListener && currentFragment.onHandleBackPressed()) {
            return
        } else {
            popFragment(true)
        }
    }

}