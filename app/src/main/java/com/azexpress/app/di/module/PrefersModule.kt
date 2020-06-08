package com.azexpress.app.di.module

import android.content.Context
import android.content.SharedPreferences
import com.app.config.di.scope.PerApplication
import com.app.data.local.prefers.PrefersHelper
import com.app.data.local.prefers.PrefersHelperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object PrefersModule {

    @Provides
    @JvmStatic
    @PerApplication
    @Named("prefers_name")
    fun providePrefersName(): String {
        return PrefersHelperImpl.FILE_NAME
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideSharedPreferences(
        context: Context,
        @Named("prefers_name") name: String
    ): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun providePrefersHelper(
        prefersHelperImpl: PrefersHelperImpl
    ): PrefersHelper {
        return prefersHelperImpl
    }
}