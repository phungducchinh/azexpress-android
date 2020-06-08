package com.lib.core.navigator

import androidx.annotation.IdRes
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentNavigator(
    private var mFragmentManager: FragmentManager?
) : IFragmentNavigator {

    companion object {
        const val TAG = "BaseNavigator"
    }

    private val mTagList = ArrayList<String>()
    private var mOnPopFragmentListener: OnPopFragmentListener? = null

    override
    fun getTagList(): ArrayList<String> {
        return mTagList
    }

    override
    fun setTagList(tagList: ArrayList<String>?) {
        this.mTagList.clear()
        tagList?.let {
            this.mTagList.addAll(tagList)
        }
    }

    override
    fun getCurrentFragment(): Fragment? {
        return if (mTagList.isEmpty()) {
            null
        } else {
            mFragmentManager?.findFragmentByTag(mTagList.last())
        }
    }

    fun setOnPopLastFragmentListener(onPopFragmentListener: OnPopFragmentListener?) {
        this.mOnPopFragmentListener = onPopFragmentListener
    }

    override
    fun pushFragment(
        @IdRes containerId: Int,
        fragment: Fragment, tag: String,
        anim: Pair<Int, Int>?
    ) {
        mFragmentManager?.let { fm ->
            if (fm.findFragmentByTag(tag) == null) {
                fm.beginTransaction().let { ft ->
                    anim?.let { anim ->
                        ft.setCustomAnimations(anim.first, anim.second)
                    }
                    ft.add(containerId, fragment, tag)
                    ft.setPrimaryNavigationFragment(fragment)
                    mTagList.add(tag)

                    fm.findFragmentById(containerId)?.let { showingFragment ->
                        ft.hide(showingFragment)
                    }
                    ft.commitNowAllowingStateLoss()
                }
            } else {
                Log.d(
                    TAG,
                    String.format(
                        "pushFragment: FAILED => fragment does existed, tag => %s",
                        tag
                    )
                )
            }
        }
    }

    override
    fun popFragment(anim: Pair<Int, Int>?) {
        val removeFragment = getCurrentFragment()
        if (removeFragment == null) {
            mOnPopFragmentListener?.onPopFragmentEmpty(this)
        } else {
            if (getTagList().size == 1) {
                mOnPopFragmentListener?.onPopLastFragment(this, removeFragment)
            } else {
                mFragmentManager?.let { fm ->
                    fm.beginTransaction().let { ft ->
                        anim?.let { anim ->
                            ft.setCustomAnimations(anim.first, anim.second)
                        }
                        ft.remove(removeFragment)
                        mTagList.remove(removeFragment.tag)

                        fm.findFragmentByTag(mTagList.last())?.let { previousFragment ->
                            ft.show(previousFragment)
                            ft.setPrimaryNavigationFragment(previousFragment)
                        }
                        ft.commitNowAllowingStateLoss()
                    }
                }
            }
        }
    }

    override
    fun showFragment(fragment: Fragment?) {
        mFragmentManager?.let { fm ->
            fm.findFragmentByTag(fragment?.tag)?.let { displayFragment ->
                fm.beginTransaction()
                    .show(displayFragment)
                    .commitNowAllowingStateLoss()
            }
        }
    }

    override
    fun showClearTopFragment(
        pair: Pair<Fragment, String>,
        anim: Pair<Int, Int>?
    ) {
        mFragmentManager?.let { fm ->
            fm.findFragmentByTag(pair.second)?.let { displayFragment ->
                fm.beginTransaction().let { ft ->
                    anim?.let { anim ->
                        ft.setCustomAnimations(anim.first, anim.second)
                    }

                    for (i in mTagList.size downTo mTagList.indexOf(pair.second)) {
                        fm.findFragmentByTag(mTagList[i])?.let { removeFragment ->
                            ft.remove(removeFragment)
                            mTagList.remove(removeFragment.tag)
                        }
                    }

                    ft.show(displayFragment)
                    ft.setPrimaryNavigationFragment(displayFragment)
                    ft.commitNowAllowingStateLoss()
                }
            }
        }
    }

    override
    fun hideFragment(fragment: Fragment?) {
        mFragmentManager?.let { fm ->
            fm.findFragmentByTag(fragment?.tag)?.let { hideFragment ->
                fm.beginTransaction()
                    .hide(hideFragment)
                    .commitNowAllowingStateLoss()
            }
        }
    }

    interface OnPopFragmentListener {
        fun onPopLastFragment(navigator: FragmentNavigator, lastFragment: Fragment?)
        fun onPopFragmentEmpty(navigator: FragmentNavigator)
    }
}