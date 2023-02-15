package com.jsworld.lottoproject.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jsworld.lottoproject.data.room.dao.LottoNumDao
import com.jsworld.lottoproject.data.room.entity.LottoNumEntity


@Database(entities = [LottoNumEntity::class], version = 1)
abstract class AppDataBase : RoomDatabase(){
    abstract fun lottoNumDao() : LottoNumDao

//    companion object{
//
//        var INSTANCE : AppDataBase? = null
//
//        fun getDataBase(context : Context) : AppDataBase{
//            return INSTANCE ?: synchronized(this){
//                val instance = Room.databaseBuilder(
//                    context,
//                    AppDataBase::class.java, "LottoNumDB"
//                ).build()
//
//                INSTANCE = instance
//
//                instance
//            }
//        }
//
//
//
//    }
}