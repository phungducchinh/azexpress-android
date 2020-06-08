package com.azexpress.app.splash.di

import com.azexpress.app.splash.SplashActivity
import com.app.config.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class SplashActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            SplashViewModelsModule::class
        ]
    )
    internal abstract fun contributeAuthActivity(): SplashActivity
}