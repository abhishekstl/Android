package com.wipro.androidexercise.dependencyinjection.module

import com.wipro.androidexercise.ui.feeds.FeedViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeFeedViewFragment(): FeedViewFragment

}