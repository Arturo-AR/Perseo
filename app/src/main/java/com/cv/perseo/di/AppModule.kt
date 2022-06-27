package com.cv.perseo.di

import com.cv.perseo.network.ImgurApi
import com.cv.perseo.network.MyInterceptor
import com.cv.perseo.network.PerseoApi
import com.cv.perseo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    @Singleton
    @Provides
    fun providesImgurApi(): ImgurApi {
        return Retrofit.Builder()
            .baseUrl(Constants.IMGUR_BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImgurApi::class.java)
    }

    @Singleton
    @Provides
    fun providesPerseoApi():PerseoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PERSEO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PerseoApi::class.java)
    }
}