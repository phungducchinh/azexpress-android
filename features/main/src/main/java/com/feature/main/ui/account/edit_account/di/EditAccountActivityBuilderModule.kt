package com.feature.main.ui.account.edit_account.di

import com.app.config.di.scope.PerActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.main.ui.account.edit_account.EditAccountActivity

@Module
abstract class EditAccountActivityBuilderModule {

    @PerActivity
    @ContributesAndroidInjector(
        modules = [
            EditAccountViewModelsModule::class,
            EditAccountFragmentBuilderModule::class
        ]
    )
    internal abstract fun contributeEditAccountActivity(): EditAccountActivity
}