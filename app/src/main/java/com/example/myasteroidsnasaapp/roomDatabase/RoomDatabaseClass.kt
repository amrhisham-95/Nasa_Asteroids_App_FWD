package com.example.myasteroidsnasaapp.roomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myasteroidsnasaapp.models.AsteroidData

@Database(entities = [AsteroidData::class], version = 1, exportSchema = false)
abstract class RoomDatabaseClass: RoomDatabase() {
    abstract fun asteroidDao(): Dao

    companion object{
        @Volatile
        private var Instance : RoomDatabaseClass?=null
        fun getInstance(context: Context):RoomDatabaseClass{
            val tempInstance = Instance
            if(tempInstance!=null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext,
                    RoomDatabaseClass::class.java,
                    "Asteroid_DataBase").build()
                Instance = instance
                return instance
            }
        }
    }
}