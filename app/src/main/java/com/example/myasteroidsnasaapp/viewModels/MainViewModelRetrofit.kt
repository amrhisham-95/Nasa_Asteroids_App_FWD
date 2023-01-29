package com.example.myasteroidsnasaapp.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myasteroidsnasaapp.models.AsteroidData
import com.example.myasteroidsnasaapp.models.PictureOfAsteroids
import com.example.myasteroidsnasaapp.repository.RetrofitRepository
import com.example.myasteroidsnasaapp.ui.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModelRetrofit : ViewModel() {

    //Make an object from (AsteroidRoomRebo) to use all fun from it :
    private val repository = RetrofitRepository()

    //Make mutableLiveData for image :
    var customImage = MutableLiveData<State<PictureOfAsteroids?>>()

    //make mutableLiveData variable
    var mutableLiveData = MutableLiveData<List<AsteroidData>>()



    //fun to get data (without room database)
    fun getViewModelFinallyData(startDate: String, endDate: String, apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
               val x = repository.parseStringToListData(apiKey, startDate, endDate)
               mutableLiveData.postValue(x)
            }
        }
    }

    //Fun to Get the picture of the day :
    fun getPictureFinally(apiKey: String) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                repository.retrofitRepoPic(apiKey).collect {
                    customImage.postValue(it)
                }
            }
        }
    }
}





