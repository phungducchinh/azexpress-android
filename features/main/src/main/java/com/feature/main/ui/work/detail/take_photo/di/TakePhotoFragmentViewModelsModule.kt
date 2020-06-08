package com.feature.main.ui.work.detail.take_photo.di

import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import com.feature.main.ui.work.detail.take_photo.TakePhotoFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TakePhotoFragmentViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(TakePhotoFragmentViewModel::class)
    internal abstract fun provideTakePhotoFragmentViewModel(viewModel: TakePhotoFragmentViewModel): ViewModel
}