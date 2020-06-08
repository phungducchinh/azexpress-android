package com.feature.main.ui.work.detail.di

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import dagger.Module
import dagger.multibindings.IntoMap
import com.lib.core.composite.FragmentComposite
import com.app.config.di.scope.PerFragment
import com.feature.main.ui.work.detail.DetailWorkActivityViewModel
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
object DetailWorkViewModelsModule {

    @Provides
    @JvmStatic
    @IntoMap
    @ViewModelKey(DetailWorkActivityViewModel::class)
    internal fun provideDetailWorkActivityViewModel(viewModel: DetailWorkActivityViewModel): ViewModel {
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