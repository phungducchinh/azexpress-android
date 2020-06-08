package com.feature.main.ui.work.detail.fragment.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.main.ui.account.edit_account.fragment.EditAccountFragmentViewModel
import com.feature.main.ui.work.detail.fragment.DetailWorkFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class DetailWorkFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(DetailWorkFragmentViewModel::class)
    internal abstract fun provideDetailWorkFragmentViewModel(viewModel: DetailWorkFragmentViewModel): ViewModel
}