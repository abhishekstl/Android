package com.wipro.androidexercise.dependencyinjection.module

import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

import com.wipro.androidex.modelbuilder.services.ServiceUrls.BASE_URL
import com.wipro.androidexercise.modelbuilder.services.FeedAPIInterface
import com.wipro.androidexercise.ui.feeds.FeedRepository

@Module(includes = [ViewModelModule::class])
object AppModule {


    @Singleton
    @Provides
    @JvmStatic
     fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun getHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Singleton
    @JvmStatic
    @Provides
    fun provideFeedApi(retrofit: Retrofit): FeedAPIInterface {
        return retrofit.create(FeedAPIInterface::class.java)
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideRepositoryInstance( apiInterface: FeedAPIInterface): FeedRepository {
        return FeedRepository(apiInterface);
    }


}
