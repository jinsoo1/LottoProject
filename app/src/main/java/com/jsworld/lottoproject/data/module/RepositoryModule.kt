package com.jsworld.lottoproject.data.module

import com.jsworld.lottoproject.data.remote.api.LottoApi
import com.jsworld.lottoproject.data.repository.LottoRepository
import com.jsworld.lottoproject.data.room.dao.LottoNumDao
import com.jsworld.lottoproject.data.room.repository.LottoNumRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideLottoRepository(lottoApi: LottoApi) = LottoRepository(lottoApi)

    @Singleton
    @Provides
    fun provideLottoNumRepository(lottoNumDao: LottoNumDao) : LottoNumRepository{
        return LottoNumRepository(lottoNumDao)
    }
}