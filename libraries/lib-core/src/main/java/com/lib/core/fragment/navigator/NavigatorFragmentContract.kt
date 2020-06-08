package com.lib.core.fragment.navigator

import androidx.fragment.app.Fragment
import com.lib.core.fragment.BaseFragmentContract
import com.lib.core.navigator.FragmentNavigator

interface NavigatorFragmentContract {

    interface View : BaseFragmentContract.View {
        fun getContainerId(): Int

        fun getNavigator(): FragmentNavigator

        fun getCurrentFragment(): Fragment?

        fun pushFragment(fragment: Fragment, tag: String, anim: Boolean)

        fun pushFragment(fragment: Fragment, tag: String, anim: Pair<Int, Int>?)

        fun popFragment(anim: Boolean)

        fun popFragment(anim: Pair<Int, Int>?)
    }

    interface ViewModel : BaseFragmentContract.ViewModel {

    }
}