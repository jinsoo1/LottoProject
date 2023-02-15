package com.jsworld.lottoproject.data.repository

import com.jsworld.lottoproject.data.remote.api.LottoApi
import com.jsworld.lottoproject.data.remote.model.response.LottoNumResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import javax.inject.Inject

class LottoRepository @Inject constructor(private val lottoApi: LottoApi) {

    suspend fun getRecentWinningNumber(drwNum: Long) : Response<LottoNumResponse> = lottoApi.getRecentWinningNumber(drwNum.toInt())




}