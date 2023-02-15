package com.jsworld.lottoproject.data.module

import android.app.Application
import androidx.room.Room
import com.jsworld.lottoproject.data.room.dao.LottoNumDao
import com.jsworld.lottoproject.data.room.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDatabaseModule {

    @Singleton
    @Provides
    internal fun provideAppDatabase(application: Application) : AppDataBase{
        return Room.databaseBuilder(application, AppDataBase::class.java, "LottoNumDB").build()
    }


    @Singleton
    @Provides
    fun provideLottoNumDao(appDataBase: AppDataBase) : LottoNumDao{
        return appDataBase.lottoNumDao()
    }

}