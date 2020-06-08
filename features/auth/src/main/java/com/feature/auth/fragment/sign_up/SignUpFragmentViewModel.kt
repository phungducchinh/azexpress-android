package com.feature.auth.fragment.sign_up

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.app.data.local.prefers.PrefersHelper
import com.app.data.model.ErrorBody
import com.app.data.remote.api.app.user.UserApi
import com.app.data.remote.api.app.user.signup.SignUpRequest
import com.app.data.remote.api.app.user.signup.SignUpResponse
import com.lib.core.fragment.BaseFragmentViewModel
import com.squareup.moshi.Moshi
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class SignUpFragmentViewModel @Inject constructor(

) : BaseFragmentViewModel(),
    SignUpFragmentContract.ViewModel {

    @Inject
    lateinit var mUserApi: UserApi

    @Inject
    lateinit var mMoshi: Moshi

    @Inject
    lateinit var mPrefersHelper: PrefersHelper

    private val mRequestSignUpLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    private val mRequestSignUpLiveDataError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    override
    fun requestSignUpLiveData(): MutableLiveData<Boolean>? {
        return mRequestSignUpLiveData
    }

    override
    fun requestSignUpErrorLiveData(): MutableLiveData<String>? {
        return mRequestSignUpLiveDataError
    }

    override
    fun requestSignUp(name: String?, email: String?, password: String?) {
        val request = SignUpRequest(name, email, password)
        mUserApi.signUp(request)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Response<SignUpResponse>> {
                override
                fun onSubscribe(d: Disposable) {
                }

                override
                fun onNext(response: Response<SignUpResponse>) {
                    if(response.isSuccessful){
                        mPrefersHelper.setToken(response.body()?.data?.token)
                        mPrefersHelper.setUser(response.body()?.data?.user)
                        requestSignUpLiveData()?.postValue(true)
                    } else{
                        val jsonAdapter = mMoshi.adapter(ErrorBody::class.java)
                        var event: ErrorBody? = null
                        response.errorBody()?.string()?.let {
                            event = jsonAdapter.fromJson(it)
                        }
                        requestSignUpErrorLiveData()?.postValue(event?.message)
                    }
                }

                override
                fun onError(e: Throwable) {
                    requestSignUpErrorLiveData()?.postValue(e.message.toString())
                }

                override
                fun onComplete() {
                    Log.d("TAGA", "Done")
                }
            })
    }
}