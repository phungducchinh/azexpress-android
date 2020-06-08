package com.feature.main.ui.work.di

import androidx.lifecycle.ViewModel
import com.feature.main.ui.work.WorkFragmentViewModel
import com.app.config.di.map_key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class WorkFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(WorkFragmentViewModel::class)
    internal abstract fun provideHomeFragmentViewModel(viewModel: WorkFragmentViewModel): ViewModel
}