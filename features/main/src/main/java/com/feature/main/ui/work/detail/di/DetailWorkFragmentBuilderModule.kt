package com.feature.main.ui.work.detail.di

import com.app.config.di.scope.PerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector
import com.feature.main.ui.work.detail.fragment.DetailWorkFragment
import com.feature.main.ui.work.detail.fragment.di.DetailWorkFragmentViewModelsModule
import com.feature.main.ui.work.detail.take_photo.TakePhotoFragment
import com.feature.main.ui.work.detail.take_photo.di.TakePhotoFragmentViewModelsModule

@Module
abstract class DetailWorkFragmentBuilderModule {

    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            DetailWorkFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeDetailWorkFragment(): DetailWorkFragment


    @PerFragment
    @ContributesAndroidInjector(
        modules = [
            TakePhotoFragmentViewModelsModule::class
        ]
    )
    abstract fun contributeTakePhotoFragment(): TakePhotoFragment
}