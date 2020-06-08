package com.azexpress.app.splash.di

import androidx.lifecycle.ViewModel
import com.azexpress.app.splash.SplashActivityViewModel
import com.app.config.di.map_key.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SplashViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(SplashActivityViewModel::class)
    internal abstract fun provideSplashActivityViewModel(viewModel: SplashActivityViewModel): ViewModel
}