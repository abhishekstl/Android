package com.wipro.androidexercise.dependencyinjection.module


import android.arch.lifecycle.ViewModel
import kotlin.reflect.KClass
import dagger.MapKey


@MustBeDocumented
@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER
)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)