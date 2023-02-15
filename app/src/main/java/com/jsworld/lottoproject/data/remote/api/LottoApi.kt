package com.jsworld.lottoproject.data.remote.api

import com.jsworld.lottoproject.data.remote.model.response.LottoNumResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

interface LottoApi {

    @GET("/common.do")
    suspend fun getRecentWinningNumber(
        @Query("drwNo") drwNum: Int,
        @Query("method") method: String = "getLottoNumber"
    ): Response<LottoNumResponse>
}