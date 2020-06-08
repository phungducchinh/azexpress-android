package com.lib.core.navigator

import androidx.annotation.IdRes
import androidx.fragment.app.Fragment

interface IFragmentNavigator {
    fun getTagList(): ArrayList<String>
    fun setTagList(tagList: ArrayList<String>?)
    fun getCurrentFragment(): Fragment?

    fun pushFragment(
        @IdRes containerId: Int,
        fragment: Fragment, tag: String,
        anim: Pair<Int, Int>?
    )

    fun popFragment(anim: Pair<Int, Int>?)

    fun showFragment(fragment: Fragment?)

    fun showClearTopFragment(
        pair: Pair<Fragment, String>,
        anim: Pair<Int, Int>?
    )

    fun hideFragment(fragment: Fragment?)

}
