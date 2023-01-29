package com.example.myasteroidsnasaapp.viewModels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.myasteroidsnasaapp.models.AsteroidData
import com.example.myasteroidsnasaapp.repository.RoomRepository
import com.example.myasteroidsnasaapp.roomDatabase.RoomDatabaseClass
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class RoomViewModel(application: Application) : AndroidViewModel(application) {

    //Make an object from (AsteroidRoomRebo) to use all fun from it :
    private val repository: RoomRepository

    //Make an object to be livedata<list<Asteroid>> , first should be init :
    var readAllAsteroidDetails: LiveData<List<AsteroidData>>

    //Make object from getTodayAsteroid :
    var getTodayAsteroid: LiveData<List<AsteroidData>>

    //Make object from getWeekAsteroid:
    var getWeekAsteroid: LiveData<List<AsteroidData>>


    //Initialize :
    init {
        val db = RoomDatabaseClass.getInstance(application)
        val asteroidDao = db.asteroidDao()
        repository = RoomRepository(asteroidDao)
        readAllAsteroidDetails = repository.readAllAsteroidsDetails

        //Get today Asteroid:
        getTodayAsteroid = repository.getTodayAsteroid

        //Get week Asteroid:
        getWeekAsteroid = repository.getWeekAsteroid
    }

    //Fun to add asteroids and i use a coroutine scope ( for learning) :
    fun addAsteroidDetails(asteroid: List<AsteroidData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addAsteroidDetails(asteroid)
        }
    }

    //Fun to update asteroids and i use a coroutine scope ( for learning):
    fun updateAsteroidDetails(asteroid: List<AsteroidData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateAsteroidDetails(asteroid)
        }
    }

    //Fun to delete asteroids and i use a coroutine scope (for learning):
    fun deleteAsteroidDetails(asteroid: List<AsteroidData>) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAsteroidDetails(asteroid)
        }
    }

    //Fun to delete all asteroids and i use a coroutine scope (for learning) :
    fun deleteAllAsteroidDetails() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllAsteroidDetails()
        }
    }

     fun getViewModelDataFromRoomDatabase(
        startDate: String,
        endDate: String,
        apiKey: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.IO) {
                repository.getDataAndPutItIntoRoomDBByDao(startDate, endDate, apiKey)
            }
        }
    }
}