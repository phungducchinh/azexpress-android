package com.feature.main.ui.account.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.main.ui.account.AccountFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AccountFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AccountFragmentViewModel::class)
    internal abstract fun provideAccountFragmentViewModel(viewModel: AccountFragmentViewModel): ViewModel
}