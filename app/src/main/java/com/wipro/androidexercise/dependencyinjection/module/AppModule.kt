package com.wipro.androidexercise.dependencyinjection.module

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import javax.inject.Singleton

import dagger.Module
import dagger.Provides
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.wipro.androidex.modelbuilder.services.ServiceUrls.BASE_URL
import com.wipro.androidexercise.BaseApplication
import com.wipro.androidexercise.modelbuilder.services.FeedAPIInterface
import com.wipro.androidexercise.modelbuilder.dataaccess.FeedRepository
import okhttp3.*
import java.util.concurrent.TimeUnit


@Module(includes = [ViewModelModule::class])
object AppModule {

    const val HTTP_RESPONSE_CACHE = (10 * 1024 * 1024).toLong()

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
    fun getOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor,cache: Cache): OkHttpClient {
           return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(getCacheInterceptor())
            .cache(cache)
            .build()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun getCache(): Cache {
        return Cache(BaseApplication.getAppInstance().cacheDir, HTTP_RESPONSE_CACHE)
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
    fun provideRepositoryInstance(apiInterface: FeedAPIInterface): FeedRepository {
        return FeedRepository(apiInterface);
    }


    private fun getNetworkInfo() : NetworkInfo?{
        return (BaseApplication.getAppInstance()
               .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
               .activeNetworkInfo
    }

    fun getCacheInterceptor(): Interceptor {

        return Interceptor { chain ->
            var request = chain.request()

            var networkInfo =  getNetworkInfo()

            request = if (networkInfo != null && networkInfo.isConnected) {

           /*
            *  If there is Internet, get the cache that was stored 1 minute ago.
            *  If the cache is older than 1 minute, then discard it,
            *  and indicate an error in fetching the response.
            *  The 'max-age' attribute is responsible for this behavior.
            */
               val cc = CacheControl.Builder()
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

                request.newBuilder()
                    .cacheControl(cc)
                    .build();

            }
            else {
               /*
                *  If there is no Internet, get the cache that was stored 7 days ago.
                *  If the cache is older than 7 days, then discard it,
                *  and indicate an error in fetching the response.
                *  The 'max-stale' attribute is responsible for this behavior.
                *  The 'only-if-cached' attribute indicates to not retrieve new data; fetch the cache only instead.
                */
                val cacheControl = CacheControl.Builder()
                    .onlyIfCached()
                    .maxStale(1, TimeUnit.DAYS)
                    .build()

                request.newBuilder()
                    .cacheControl(cacheControl)
                    .build();
            }
            // Add the modified request to the chain.
            chain.proceed(request)
        }

     }

}








