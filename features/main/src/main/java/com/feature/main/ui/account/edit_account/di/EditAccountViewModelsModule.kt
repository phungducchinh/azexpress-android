package com.feature.main.ui.account.edit_account.di

import android.util.Log
import androidx.lifecycle.ViewModel
import com.app.config.di.map_key.ViewModelKey
import dagger.Module
import dagger.multibindings.IntoMap
import com.lib.core.composite.FragmentComposite
import com.app.config.di.scope.PerFragment
import com.feature.main.ui.account.edit_account.EditAccountActivityViewModel
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

@Module
object EditAccountViewModelsModule {

    @Provides
    @JvmStatic
    @IntoMap
    @ViewModelKey(EditAccountActivityViewModel::class)
    internal fun provideEditAccountActivityViewModel(viewModel: EditAccountActivityViewModel): ViewModel {
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