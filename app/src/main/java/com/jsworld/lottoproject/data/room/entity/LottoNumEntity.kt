package com.jsworld.lottoproject.data.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import dagger.Provides


@Entity(tableName = "LottoNum")
data class LottoNumEntity(
    @PrimaryKey(autoGenerate = true) var idx : Int = 0,
    var drwNo: Int,
    var drwtNo1: Int,
    var drwtNo2: Int,
    var drwtNo3: Int,
    var drwtNo4: Int,
    var drwtNo5: Int,
    var drwtNo6: Int,
    var bnusNo: Int
)
