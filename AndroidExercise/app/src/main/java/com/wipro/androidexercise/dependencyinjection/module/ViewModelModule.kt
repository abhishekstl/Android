package com.wipro.androidexercise.dependencyinjection.module


import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.wipro.androidexercise.ui.feeds.FeedListViewModel


import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton


@Singleton
class FeedViewModelFactory @Inject constructor(
    private val creators: Map<Class<out ViewModel>, @JvmSuppressWildcards Provider<ViewModel>>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val creator = creators[modelClass] ?: creators.entries.firstOrNull {
            modelClass.isAssignableFrom(it.key)
        }?.value ?: throw IllegalArgumentException("unknown model class $modelClass")
        try {
            @Suppress("UNCHECKED_CAST")
            return creator.get() as T
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }
}

@Suppress("unused")
@Module

abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(FeedListViewModel::class)
    abstract fun bindUserViewModel(feedListViewModel: FeedListViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: FeedViewModelFactory): ViewModelProvider.Factory
}