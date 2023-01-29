package com.example.myasteroidsnasaapp.roomDatabase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.myasteroidsnasaapp.models.AsteroidData

@androidx.room.Dao
interface Dao {

        //To read all data in Asteroid data base :
        @Query("SELECT * FROM asteroid_table ORDER BY closeApproachDate ASC")
        fun readAllAsteroidDetails() : LiveData<List<AsteroidData>>

        //To insert data in Asteroid data base :
        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun addAsteroidDetails(asteroid:List<AsteroidData>)

        //To update data in Asteroid data base (i don't need it now but for learning):
        @Update
        suspend fun updateAsteroidDetails(asteroid: List<AsteroidData>)

        //To delete data in Asteroid data base (i don't need it now but for learning):
        @Delete
        suspend fun deleteAsteroidDetails(asteroid: List<AsteroidData>)

        //To delete all data in Asteroid data base ( i don't need it now but for learning):
        @Query("DELETE FROM asteroid_table")
        suspend fun deleteAllAsteroidDetails()

        //To delete previous day asteroid :
        @Query("DELETE FROM asteroid_table WHERE closeApproachDate == :date")
        fun deletePreviousDayAsteroids(date: String)

        //To read & get asteroid for a week :
        @Query("SELECT * FROM asteroid_table WHERE closeApproachDate  BETWEEN :startDate AND :endDate ORDER BY closeApproachDate ASC")
        fun getWeekAsteroids(startDate: String, endDate: String): LiveData<List<AsteroidData>>

        //To read & get asteroid for Today :
        @Query("SELECT * FROM asteroid_table WHERE closeApproachDate == :todayDate ORDER BY closeApproachDate ASC ")
        fun getTodayAsteroids(todayDate: String): LiveData<List<AsteroidData>>

    }
