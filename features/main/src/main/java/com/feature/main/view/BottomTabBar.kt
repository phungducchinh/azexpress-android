package com.feature.main.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringDef
import com.feature.main.R
import com.lib.core.widget.BaseConstraintLayout

class BottomTabBar : BaseConstraintLayout,
    View.OnClickListener {

    @Retention(AnnotationRetention.SOURCE)
    @StringDef(
        Tab.None,
        Tab.Work,
        Tab.BarCode,
        Tab.Account
    )
    annotation class Tab {
        companion object {
            const val None = "Tab.None"
            const val Work = "Tab.Work"
            const val BarCode = "Tab.BarCode"
            const val Account = "Tab.Account"
        }
    }

    private var mWorkConstraintLayout: BaseConstraintLayout? = null
    private var mBarCodeConstraintLayout: BaseConstraintLayout? = null
    private var mAccountConstraintLayout: BaseConstraintLayout? = null

    @Tab
    var mCurrentTab = Tab.None

    private var mOnTabClickListener: OnTabClickListener? = null

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    fun setOnTabClickListener(onTabClickListener: OnTabClickListener?) {
        this.mOnTabClickListener = onTabClickListener
    }

    override
    fun init(ctx: Context, attrs: AttributeSet?) {
        super.init(ctx, attrs)
        LayoutInflater.from(context).inflate(R.layout.layout_bottom_tab_bar, this)

        mWorkConstraintLayout = findViewById(R.id.work_constraint_layout)
        mBarCodeConstraintLayout = findViewById(R.id.barcode_constraint_layout)
        mAccountConstraintLayout = findViewById(R.id.account_constraint_layout)

        initListener()
    }

    private fun initListener() {
        mWorkConstraintLayout?.setOnClickListener(this)
        mBarCodeConstraintLayout?.setOnClickListener(this)
        mAccountConstraintLayout?.setOnClickListener(this)
    }

    override
    fun onClick(view: View?) {
        when (view) {
            mWorkConstraintLayout -> mOnTabClickListener?.onTabWorkClick()
            mBarCodeConstraintLayout -> mOnTabClickListener?.onTabBarCodeClick()
            mAccountConstraintLayout -> mOnTabClickListener?.onTabAccountClick()
        }
    }

    fun updateCurrentTab() {
        mWorkConstraintLayout?.isSelected = mCurrentTab == Tab.Work
        mBarCodeConstraintLayout?.isSelected = mCurrentTab == Tab.BarCode
        mAccountConstraintLayout?.isSelected = mCurrentTab == Tab.Account
    }

    interface OnTabClickListener {
        fun onTabWorkClick()
        fun onTabBarCodeClick()
        fun onTabAccountClick()
    }
}