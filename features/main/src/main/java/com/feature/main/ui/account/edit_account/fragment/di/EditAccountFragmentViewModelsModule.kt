package com.feature.main.ui.account.edit_account.fragment.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.main.ui.account.edit_account.fragment.EditAccountFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EditAccountFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(EditAccountFragmentViewModel::class)
    internal abstract fun provideEditAccountFragmentViewModel(viewModel: EditAccountFragmentViewModel): ViewModel
}