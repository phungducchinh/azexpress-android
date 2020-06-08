package com.feature.main.ui.work.detail.di

import com.app.config.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.main.ui.account.edit_account.EditAccountActivity
import com.feature.main.ui.work.detail.DetailWorkActivity

@Module
abstract class DetailWorkActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            DetailWorkViewModelsModule::class,
            DetailWorkFragmentBuilderModule::class
        ]
    )
    internal abstract fun contributeDetailWorkActivity(): DetailWorkActivity
}