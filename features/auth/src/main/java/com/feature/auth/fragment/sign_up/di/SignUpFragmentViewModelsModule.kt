package com.feature.auth.fragment.sign_up.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.auth.fragment.sign_up.SignUpFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SignUpFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SignUpFragmentViewModel::class)
    internal abstract fun provideSignUpFragmentViewModel(viewModel: SignUpFragmentViewModel): ViewModel
}