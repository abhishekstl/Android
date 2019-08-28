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
        appInstance = this
    }

    override fun activityInjector() = dispatchingAndroidInjector

    companion object {

        @JvmField
        var appInstance: BaseApplication? = null

        @JvmStatic fun getAppInstance(): BaseApplication {
            return appInstance as BaseApplication
        }
    }
}






