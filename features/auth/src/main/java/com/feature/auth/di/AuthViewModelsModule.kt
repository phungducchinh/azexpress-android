package com.feature.auth.di

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import dagger.Module
import dagger.multibindings.IntoMap
import com.feature.auth.AuthActivityViewModel
import com.lib.core.composite.FragmentComposite
import com.app.config.di.scope.PerFragment
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
object AuthViewModelsModule {

    @Provides
    @JvmStatic
    @IntoMap
    @ViewModelKey(AuthActivityViewModel::class)
    internal fun provideAuthActivityViewModel(viewModel: AuthActivityViewModel): ViewModel {
        return viewModel
    }

    @Provides
    @JvmStatic
    @PerFragment
    internal fun provideFragmentComposite(): FragmentComposite {
        val fragmentComposite = FragmentComposite(CompositeDisposable())
        Log.d(
            "FragmentModule",
            "FragmentModule Provides PerFragment provideFragmentComposite: $fragmentComposite"
        )
        return fragmentComposite
    }
}