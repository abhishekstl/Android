package com.wipro.androidexercise.dependencyinjection.components

import android.app.Application

import com.wipro.androidexercise.BaseApplication
import com.wipro.androidexercise.dependencyinjection.module.AppModule
import javax.inject.Singleton
import dagger.BindsInstance
import dagger.Component
import com.wipro.androidexercise.dependencyinjection.module.FeedListActivityModule
import dagger.android.AndroidInjectionModule


@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, FeedListActivityModule::class])
interface AppComponent  {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder
        fun build(): AppComponent

    }

    fun inject(baseApp: BaseApplication)
}


