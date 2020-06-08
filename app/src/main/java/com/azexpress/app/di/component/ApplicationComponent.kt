package com.azexpress.app.di.component

import android.content.Context
import com.azexpress.app.application.MyApplication
import com.azexpress.app.di.module.ApplicationModule
import com.azexpress.app.splash.di.SplashActivityBuilderModule
import com.app.config.di.scope.PerApplication
import com.feature.auth.di.AuthActivityBuilderModule
import com.feature.main.di.MainActivityBuilderModule
import com.feature.main.ui.account.edit_account.di.EditAccountActivityBuilderModule
import com.feature.main.ui.work.detail.di.DetailWorkActivityBuilderModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule

@PerApplication
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        SplashActivityBuilderModule::class,
        AuthActivityBuilderModule::class,
        MainActivityBuilderModule::class,
        DetailWorkActivityBuilderModule::class,
        EditAccountActivityBuilderModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<MyApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: MyApplication): Builder

        @BindsInstance
        fun context(applicationContext: Context): Builder

        fun build(): ApplicationComponent
    }
}