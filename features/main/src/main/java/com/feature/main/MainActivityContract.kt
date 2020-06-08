package com.feature.main

import androidx.fragment.app.Fragment
import com.feature.main.view.BottomTabBar
import com.lib.core.activity.navigator.NavigatorActivityContract
import com.lib.core.navigator.FragmentNavigator

interface MainActivityContract {
    interface View : NavigatorActivityContract.View {
        fun getFragment(@BottomTabBar.Tab tab: String?): Pair<String, Fragment>?
        fun getNavigatorByTab(@BottomTabBar.Tab tab: String?): FragmentNavigator
        fun showTab(@BottomTabBar.Tab showing: String, @BottomTabBar.Tab upcoming: String)
        fun hideTab(@BottomTabBar.Tab tab: String)
    }

    interface ViewModel : NavigatorActivityContract.ViewModel {
    }
}