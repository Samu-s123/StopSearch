package com.univaq.stopsearch.di

import android.content.Context
import androidx.room.Room
import com.univaq.stopsearch.common.BASE_URL
import com.univaq.stopsearch.data.local.StopDatabase
import com.univaq.stopsearch.data.local.StopRoomRepository
import com.univaq.stopsearch.data.remote.StopRetrofitRepository
import com.univaq.stopsearch.data.remote.StopService
import com.univaq.stopsearch.domain.repository.StopLocalRepository
import com.univaq.stopsearch.domain.repository.StopRemoteRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun retrofitClient(): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun stopService(retrofit: Retrofit): StopService =
        retrofit.create(StopService::class.java)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule{

    @Binds
    @Singleton
    abstract fun remoteRepository(repository: StopRetrofitRepository): StopRemoteRepository

    @Binds
    @Singleton
    abstract fun localRepository(repository: StopRoomRepository): StopLocalRepository
}

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun database(@ApplicationContext context: Context): StopDatabase =
        Room.databaseBuilder(
            context,
            StopDatabase::class.java, "stop_database")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun stopDao(database: StopDatabase) = database.getStopDao()

    @Provides
    @Singleton
    fun tripTimeDao(database: StopDatabase) = database.getTripTimeDao()
}