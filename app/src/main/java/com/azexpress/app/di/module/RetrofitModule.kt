package com.azexpress.app.di.module

import android.content.Context
import android.util.Log
import com.app.config.di.scope.PerApplication
import com.app.data.local.prefers.PrefersHelper
import com.app.data.model.ErrorBody
import com.app.data.remote.api.app.task.TaskApi
import com.app.data.remote.api.app.user.UserApi
import com.app.data.remote.interceptor.AddHeadersInterceptor
import com.app.data.remote.interceptor.UnauthorizedInterceptor
import com.azexpress.app.BuildConfig
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
object RetrofitModule {

    private const val TAG = "RetrofitModule"
    private const val CACHE_SIZE = 4L * 1024 * 1024 //4 MB

    @Provides
    @JvmStatic
    @PerApplication
    @Named("base_url")
    fun provideBaseUrl(): String {
        return BuildConfig.API_BASE_URL
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideAddHeadersInterceptor(preferHelper: PrefersHelper): AddHeadersInterceptor {
        return AddHeadersInterceptor(preferHelper)
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideUnauthorizedInterceptor(unauthorizedInterceptor: UnauthorizedInterceptor): Interceptor {
        return unauthorizedInterceptor
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideOkHttpCache(context: Context): Cache {
        return Cache(
            context.cacheDir,
            CACHE_SIZE
        )
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        addHeadersInterceptor: AddHeadersInterceptor,
        unauthorizedInterceptor: UnauthorizedInterceptor,
        cache: Cache
    ): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor(addHeadersInterceptor)
            .addInterceptor(httpLoggingInterceptor)
            .addNetworkInterceptor(unauthorizedInterceptor)
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS).build()
        Log.d(TAG, "Provides PerApplication provideOkHttpClient: $okHttpClient")
        return okHttpClient
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
//            .add(CardAdapter())
            .build()
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideRetrofit(
        @Named("base_url") baseUrl: String,
        moshi: Moshi,
        okHttpClient: OkHttpClient
    ): Retrofit {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()
        Log.d(TAG, "Provides PerApplication provideRetrofit: $retrofit")
        return retrofit
    }

    @Provides
    @JvmStatic
    @PerApplication
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }


    @Provides
    @JvmStatic
    @PerApplication
    fun provideTaskApi(retrofit: Retrofit): TaskApi {
        return retrofit.create(TaskApi::class.java)
    }

}

