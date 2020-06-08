package com.feature.main.ui.account.edit_account.di

import com.app.config.di.scope.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.main.ui.account.edit_account.fragment.EditAccountFragment
import com.feature.main.ui.account.edit_account.fragment.di.EditAccountFragmentViewModelsModule

@Module
abstract class EditAccountFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            EditAccountFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeEditAccountFragment(): EditAccountFragment
}