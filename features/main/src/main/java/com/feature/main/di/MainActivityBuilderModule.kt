package com.feature.main.di

import com.feature.main.MainActivity
import com.app.config.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            MainViewModelsModule::class,
            MainFragmentBuilderModule::class
        ]
    )
    internal abstract fun contributeMainActivity(): MainActivity
}