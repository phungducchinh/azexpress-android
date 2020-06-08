package com.feature.auth.di

import com.app.config.di.scope.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.auth.fragment.sign_in.SignInFragment
import com.feature.auth.fragment.sign_in.di.SignInFragmentViewModelsModule
import com.feature.auth.fragment.sign_up.SignUpFragment
import com.feature.auth.fragment.sign_up.di.SignUpFragmentViewModelsModule

@Module
abstract class AuthFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            SignUpFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeSignUpFragment(): SignUpFragment

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            SignInFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeSignInFragment(): SignInFragment


}