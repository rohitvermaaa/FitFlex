package com.example.fitflex

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [HistoryEntity::class] , version = 1)
abstract class HistoryDatabase : RoomDatabase() {

    abstract fun historyDao() : HistoryDao

    companion object{
        @Volatile
        private var INSTANCES : HistoryDatabase? = null

        fun getInstance(context : Context):HistoryDatabase {
            synchronized(this){
                var instance = INSTANCES
                if (instance == null){
                    instance = Room.databaseBuilder(context.applicationContext ,
                        HistoryDatabase::class.java, "history_database").fallbackToDestructiveMigration().build()
                    INSTANCES = instance
                }
                return instance
            }
        }
    }
}