package com.lib.core.activity.navigator

import androidx.fragment.app.Fragment
import com.lib.core.activity.BaseActivityContract
import com.lib.core.navigator.FragmentNavigator

interface NavigatorActivityContract {

    interface View : BaseActivityContract.View {
        fun getContainerId(): Int

        fun getNavigator(): FragmentNavigator

        fun getCurrentFragment(): Fragment?

        fun pushFragment(fragment: Fragment, tag: String, anim: Boolean)

        fun pushFragment(fragment: Fragment, tag: String, anim: Pair<Int, Int>?)

        fun popFragment(anim: Boolean)

        fun popFragment(anim: Pair<Int, Int>?)

        fun showFragment(fragment: Fragment?)

        fun hideFragment(fragment: Fragment?)
    }

    interface ViewModel : BaseActivityContract.ViewModel
}