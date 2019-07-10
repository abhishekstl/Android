package com.wipro.androidexercise.dependencyinjection.module

import com.wipro.androidexercise.ui.feeds.FeedsListActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FeedListActivityModule {
    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeFeedListActivity(): FeedsListActivity
}