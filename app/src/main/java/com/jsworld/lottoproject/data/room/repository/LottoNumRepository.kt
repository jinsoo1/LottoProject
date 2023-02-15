package com.jsworld.lottoproject.data.room.repository

import com.jsworld.lottoproject.data.room.dao.LottoNumDao
import com.jsworld.lottoproject.data.room.entity.LottoNumEntity
import javax.inject.Inject

class LottoNumRepository @Inject constructor(
    private val lottoNumDao: LottoNumDao
) {


    suspend fun insertLottoNum(lottoNumEntity: LottoNumEntity){
        lottoNumDao.insertDrwNoLottoNum(lottoNumEntity)
    }

    suspend fun getAllLottoNum(drwNo : Int) : List<LottoNumEntity>{
        return lottoNumDao.getDrwNoLottoNum(drwNo)
    }



}