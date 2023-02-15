package com.jsworld.lottoproject.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.jsworld.lottoproject.data.room.entity.LottoNumEntity


@Dao
interface LottoNumDao {
    @Query("SELECT * FROM LottoNum WHERE drwNo = :drwNo")
    suspend fun getDrwNoLottoNum(drwNo : Int) : List<LottoNumEntity>

    @Insert
    suspend fun insertDrwNoLottoNum(lottoNumEntity : LottoNumEntity)

    @Delete
    suspend fun deleteLottoNum(lottoNumEntity: LottoNumEntity)

    @Query("DELETE FROM LottoNum WHERE idx = :idx")
    suspend fun deleteLottoNumByIdx(idx : Int)



}