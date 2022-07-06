package com.cv.perseo.di

import android.content.Context
import androidx.room.Room
import com.cv.perseo.data.database.PerseoDatabase
import com.cv.perseo.data.database.PerseoDatabaseDao
import com.cv.perseo.network.ImgurApi
import com.cv.perseo.network.MyInterceptor
import com.cv.perseo.network.PerseoApi
import com.cv.perseo.repository.DatabaseRepository
import com.cv.perseo.repository.ImgurRepository
import com.cv.perseo.repository.PerseoRepository
import com.cv.perseo.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(MyInterceptor())
    }.build()

    /**
     * Apis Providers
     */
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
    fun providesPerseoApi(): PerseoApi {
        return Retrofit.Builder()
            .baseUrl(Constants.PERSEO_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(PerseoApi::class.java)
    }

    /**
     * Repositories Providers
     */
    @Singleton
    @Provides
    fun providePerseoRepository(api: PerseoApi) = PerseoRepository(api)

    @Singleton
    @Provides
    fun provideImgurRepository(api: ImgurApi) = ImgurRepository(api)

    @Singleton
    @Provides
    fun provideDatabaseRepository(dao: PerseoDatabaseDao) = DatabaseRepository(dao)

    /**
     * Database Providers
     */
    @Singleton
    @Provides
    fun providePerseoDao(perseoDatabase: PerseoDatabase): PerseoDatabaseDao =
        perseoDatabase.perseoDao()

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): PerseoDatabase =
        Room.databaseBuilder(
            context,
            PerseoDatabase::class.java,
            "perseo_database"
        )
            .fallbackToDestructiveMigration()
            .build()
}