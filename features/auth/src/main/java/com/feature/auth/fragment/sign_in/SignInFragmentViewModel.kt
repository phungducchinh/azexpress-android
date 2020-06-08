package com.feature.auth.fragment.sign_in

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.data.local.prefers.PrefersHelper
import com.app.data.model.ErrorBody
import com.app.data.remote.api.app.user.UserApi
import com.app.data.remote.api.app.user.login.LoginRequest
import com.app.data.remote.api.app.user.login.LoginResponse
import com.lib.core.fragment.BaseFragmentViewModel
import com.squareup.moshi.Moshi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject


class SignInFragmentViewModel @Inject constructor(

) : BaseFragmentViewModel(),
    SignInFragmentContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    @Inject
    lateinit var mMoshi: Moshi

    @Inject
    lateinit var mPrefersHelper: PrefersHelper

    private val mRequestSignInLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val mRequestSignInLiveDataError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override
    fun requestSignInLiveData(): MutableLiveData<Boolean>? {
        return mRequestSignInLiveData
    }

    override
    fun requestSignInErrorLiveData(): MutableLiveData<String>? {
        return mRequestSignInLiveDataError
    }

    override
    fun requestLogin(email: String?, password: String?) {
        val request = LoginRequest(email, password)
        mUserApi.login(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<LoginResponse>> {
                override
                fun onSubscribe(d: Disposable) {

                }

                override
                fun onNext(response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        mPrefersHelper.setToken(response.body()?.data?.token)
                        mPrefersHelper.setUser(response.body()?.data?.user)
                        requestSignInLiveData()?.postValue(true)
                    } else {
                        val jsonAdapter = mMoshi.adapter(ErrorBody::class.java)
                        var event: ErrorBody? = null
                        response.errorBody()?.string()?.let {
                            event = jsonAdapter.fromJson(it)
                        }
                        requestSignInErrorLiveData()?.postValue(event?.message)
                    }
                }

                override
                fun onError(e: Throwable) {
                    requestSignInErrorLiveData()?.postValue(e.message.toString())
                }

                override
                fun onComplete() {
                    Log.d(TAG, "Done")
                }
            })
    }
}