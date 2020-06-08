package com.azexpress.app.di.module

import com.azexpress.app.application.MyApplication
import com.app.config.di.scope.PerApplication
import com.lib.core.application.ApplicationLifecycleCallback
import com.lib.core.application.ApplicationLifecycleManager
import com.lib.core.application.BaseApplication
import com.lib.network.NetworkHelper
import com.lib.network.NetworkHelperImpl
import com.lib.network.OnConnectionChangedListener
import com.lib.network.OnInternetConnectionChangedListener
import com.app.data.remote.interceptor.UnauthorizedInterceptor
import dagger.Module
import dagger.Provides

@Module(
    includes = [
        PrefersModule::class,
        DatabaseModule::class,
        RetrofitModule::class
    ]
)
object ApplicationModule {

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideBaseApplication(myApplication: MyApplication): BaseApplication {
        return myApplication
    }

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideApplicationLifecycleCallback(application: BaseApplication): ApplicationLifecycleCallback {
        return application
    }

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideApplicationLifecycleManager(callback: ApplicationLifecycleCallback): ApplicationLifecycleManager {
        return ApplicationLifecycleManager(callback)
    }

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideConnectionChangeListener(application: BaseApplication): OnConnectionChangedListener {
        return application
    }

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideInternetConnectionChangeListener(application: BaseApplication): OnInternetConnectionChangedListener {
        return application
    }

    @Provides
    @PerApplication
    @JvmStatic
    internal fun provideOnTokenExpiredListener(application: MyApplication): UnauthorizedInterceptor.OnTokenExpiredListener {
        return application
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideNetworkHelper(networkHelper: NetworkHelperImpl): NetworkHelper {
        return networkHelper
    }
}