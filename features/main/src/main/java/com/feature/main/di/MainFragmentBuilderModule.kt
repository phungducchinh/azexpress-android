package com.feature.main.di

import com.feature.main.ui.work.WorkFragment
import com.feature.main.ui.work.di.WorkFragmentViewModelsModule
import com.app.config.di.scope.PerFragment
import com.feature.main.ui.barcode.ScanBarCodeFragment
import com.feature.main.ui.barcode.di.ScanBarCodeFragmentViewModelsModule
import com.feature.main.ui.account.AccountFragment
import com.feature.main.ui.account.di.AccountFragmentViewModelsModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            WorkFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeWorkFragment(): WorkFragment

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            ScanBarCodeFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeScanBarCodeFragment(): ScanBarCodeFragment

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            AccountFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeAccountFragment(): AccountFragment
}