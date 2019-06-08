package com.wipro.androidexercise

import android.app.Activity
import android.app.Application
import dagger.android.*
import javax.inject.Inject


class BaseApplication : Application(), HasActivityInjector   {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}





