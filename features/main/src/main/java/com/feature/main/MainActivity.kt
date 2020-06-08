package com.feature.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.feature.main.ui.barcode.ScanBarCodeFragment
import com.feature.main.ui.work.WorkFragment
import com.feature.main.ui.account.AccountFragment
import com.feature.main.view.BottomTabBar
import com.lib.core.activity.navigator.NavigatorActivity
import com.lib.core.navigator.FragmentNavigator

class MainActivity : NavigatorActivity(),
    MainActivityContract.View,
    BottomTabBar.OnTabClickListener{

    private val mWorkNavigator: FragmentNavigator by lazy {
        FragmentNavigator(supportFragmentManager)
    }
    private val mBarCodeNavigator: FragmentNavigator by lazy {
        FragmentNavigator(supportFragmentManager)
    }
    private val mAccountNavigator: FragmentNavigator by lazy {
        FragmentNavigator(supportFragmentManager)
    }

    private lateinit var mBottomTabBar: BottomTabBar

    private lateinit var mViewModel: MainActivityViewModel

    override
    fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override
    fun getContainerId(): Int {
        return R.id.activity_container
    }

    override
    fun performDependencyInjection() {
        mViewModel = ViewModelProvider(
            this,
            mViewModelFactory.get()
        )[MainActivityViewModel::class.java]
    }

    override
    fun getNavigator(): FragmentNavigator {
        return getNavigatorByTab(mBottomTabBar.mCurrentTab)
    }

    override
    fun init(savedInstanceState: Bundle?) {
        mBottomTabBar = findViewById(R.id.bottom_tab_bar)

        super.init(savedInstanceState)

        initData()
        initListener()

    }

    private fun initData() {
        showTab(BottomTabBar.Tab.None, BottomTabBar.Tab.Work)
    }

    private fun initListener() {
        mBottomTabBar.setOnTabClickListener(this)
        mWorkNavigator.setOnPopLastFragmentListener(this)
        mBarCodeNavigator.setOnPopLastFragmentListener(this)
        mAccountNavigator.setOnPopLastFragmentListener(this)

    }

    override
    fun onTabWorkClick() {
        showTab(mBottomTabBar.mCurrentTab, BottomTabBar.Tab.Work)
    }

    override
    fun onTabBarCodeClick() {
        showTab(mBottomTabBar.mCurrentTab, BottomTabBar.Tab.BarCode)
    }

    override
    fun onTabAccountClick() {
        showTab(mBottomTabBar.mCurrentTab, BottomTabBar.Tab.Account)
        /*val translateBundle = ActivityOptions
            .makeCustomAnimation(this, R.anim.enter_from_right, R.anim.do_nothing)
            .toBundle()*/
    }
    override
    fun getFragment(@BottomTabBar.Tab tab: String?): Pair<String, Fragment>? {
        return when (tab) {
            BottomTabBar.Tab.Work -> Pair(WorkFragment.TAG, WorkFragment.newInstance())
            BottomTabBar.Tab.BarCode -> Pair(ScanBarCodeFragment.TAG, ScanBarCodeFragment.newInstance())
            BottomTabBar.Tab.Account -> Pair(AccountFragment.TAG, AccountFragment.newInstance())
            else -> null
        }
    }

    override
    fun getNavigatorByTab(@BottomTabBar.Tab tab: String?): FragmentNavigator {
        return when (tab) {
            BottomTabBar.Tab.Work -> mWorkNavigator
            BottomTabBar.Tab.BarCode -> mBarCodeNavigator
            BottomTabBar.Tab.Account -> mAccountNavigator
            else -> super.getNavigator()
        }
    }

    override
    fun showTab(@BottomTabBar.Tab showing: String, @BottomTabBar.Tab upcoming: String) {
        if (showing == upcoming) {
            Log.d(TAG, "same tab!")
        } else {
            mBottomTabBar.mCurrentTab = upcoming
            mBottomTabBar.updateCurrentTab()
            val displayFragment = getCurrentFragment()
            if (displayFragment == null) {
                getFragment(upcoming)?.let {
                    pushFragment(it.second, it.first, false)
                }
            } else {
                showFragment(displayFragment)
            }
            //hide previous tab
            hideTab(showing)
        }
    }

    override
    fun hideTab(@BottomTabBar.Tab tab: String) {
        getNavigatorByTab(tab).let {
            it.hideFragment(it.getCurrentFragment())
        }
    }

    override
    fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.d("FLAG_TEST", "onNewIntent")
    }
}