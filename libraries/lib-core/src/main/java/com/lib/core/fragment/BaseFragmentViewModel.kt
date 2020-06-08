package com.lib.core.fragment

import com.lib.core.viewmodel.BaseViewModel
import javax.inject.Inject

abstract class BaseFragmentViewModel : BaseViewModel(), BaseFragmentContract.ViewModel {

    companion object {
        var TAG = "BaseFragmentViewModel"
    }

    /*@Inject
    lateinit var mActivityComposite: ActivityComposite

    @Inject
    lateinit var mFragmentComposite: FragmentComposite
*/
    /*override
    fun getLoadingEvent(): ActivityLoadingEvent {
        return ActivityLoadingEvent()
    }*/

    override
    fun onCleared() {
        /*Log.d(
            AppConstants.TAG,
            String.format("onCleared: %s, %s, %s", mFragmentComposite, mActivityLoadingEvent, this)
        )*/
        super.onCleared()
    }
}