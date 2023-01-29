package com.example.myasteroidsnasaapp.repository

import com.example.myasteroidsnasaapp.models.*
import com.example.myasteroidsnasaapp.ui.State
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.json.JSONObject


class RetrofitRepository {

    //Fun to get asteroid from Api nasa , and i will catch this data through work manager , go and see :
    private suspend fun retrofitRepositoryGetData(
        startDate: String,
        endDate: String,
        apiKey: String
    ) =
        AsteroidObjectApi.retrofitAsteroidService.getAsteroidDetails(startDate, endDate, apiKey)


    //to parse between string data to list of asteroid data (without room data base)
    suspend fun parseStringToListData(
        startDate: String,
        endDate: String,
        apiKey: String
    ) = parseAsteroidsJsonResult(JSONObject(retrofitRepositoryGetData(startDate, endDate, apiKey)))


    //suspend fun to get image and state when downloading in my app using flow :
    suspend fun retrofitRepoPic(apiKey: String): Flow<State<PictureOfAsteroids?>> {
        return flow {
            emit(State.Loading)
            val result = PictureOfDayObjectApi.retrofitPictureService.getImageOfDay(apiKey)
            if (result.isSuccessful) {
                emit(State.Success(result.body()))
            } else {
                emit(State.Error(result.message()))
            }
        }
    }
}