package com.khush.moviesapp.di.module

import com.khush.moviesapp.common.Const
import com.khush.moviesapp.data.network.ApiInterface
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Singleton
    @Provides
    fun provideNetworkService(): ApiInterface {
        return Retrofit
            .Builder()
            .baseUrl(Const.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)
    }
}