package com.feature.main.ui.barcode.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.main.ui.barcode.ScanBarCodeFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ScanBarCodeFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ScanBarCodeFragmentViewModel::class)
    internal abstract fun provideCalendarFragmentViewModel(viewModel: ScanBarCodeFragmentViewModel): ViewModel
}