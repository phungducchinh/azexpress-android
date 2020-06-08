package com.azexpress.app.di.module

import android.content.Context
import com.app.config.di.scope.PerApplication
import com.app.data.local.db.AppDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
object DatabaseModule {

    @Provides
    @JvmStatic
    @PerApplication
    @Named("database_name")
    fun provideDatabaseName(): String {
        return AppDatabase.DATABASE_NAME
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideAppDatabase(context: Context, @Named("database_name") name: String): AppDatabase {
        return AppDatabase.getDatabase(context, name)
    }
}