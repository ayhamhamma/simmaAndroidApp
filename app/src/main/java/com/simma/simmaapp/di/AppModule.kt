package com.simma.simmaapp.di

import android.util.Log.e
import com.google.gson.GsonBuilder
import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.repository.RepositoryImp
import com.vs.simma.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun repositoryProvider(@NoNull apiServices: ApiServices , @WithNull apiServicesWithNull : ApiServices ): Repository {
        return RepositoryImp(apiServices,apiServicesWithNull)
    }

    @NoNull
    @Singleton
    @Provides
    fun provideClient(): ApiServices {
        val baseUrl = "http://3.21.242.126/"
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .build()

        val retrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return retrofitInstance.create(ApiServices::class.java)
    }

    @WithNull
    @Singleton
    @Provides
    fun provideClientWithNull(): ApiServices {
        val baseUrl = "http://3.145.15.33/"
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideInterceptorWithNull())
            .build()

        val retrofitInstance = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().serializeNulls().create()))
            .build()

        return retrofitInstance.create(ApiServices::class.java)
    }

    private fun provideInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideInterceptorWithNull(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class WithNull

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NoNull
