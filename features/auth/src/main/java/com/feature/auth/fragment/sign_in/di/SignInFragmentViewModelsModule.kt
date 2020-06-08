package com.feature.auth.fragment.sign_in.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.auth.fragment.sign_in.SignInFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SignInFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignInFragmentViewModel::class)
    internal abstract fun provideSignInFragmentViewModel(viewModel: SignInFragmentViewModel): ViewModel
}