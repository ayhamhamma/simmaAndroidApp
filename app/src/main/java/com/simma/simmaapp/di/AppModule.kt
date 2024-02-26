package com.simma.simmaapp.di

import com.simma.simmaapp.remote.Repository
import com.simma.simmaapp.repository.RepositoryImp
import com.vs.simma.api.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun repositoryProvider(apiServices: ApiServices): Repository {
        return RepositoryImp(apiServices)
    }

    @Singleton
    @Provides
    fun provideClient(): ApiServices {
        val base_url  ="http://3.145.15.33/"
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideInterceptor())
            .build()

        val retrofitInstance =
            Retrofit.Builder()
                .baseUrl(base_url)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val services = retrofitInstance.create(ApiServices::class.java)
        return services
    }
    private fun provideInterceptor() : HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}