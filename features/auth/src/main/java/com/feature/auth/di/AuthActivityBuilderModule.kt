package com.feature.auth.di

import com.app.config.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.auth.AuthActivity

@Module
abstract class AuthActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            AuthViewModelsModule::class,
            AuthFragmentBuilderModule::class
        ]
    )
    internal abstract fun contributeAuthActivity(): AuthActivity
}