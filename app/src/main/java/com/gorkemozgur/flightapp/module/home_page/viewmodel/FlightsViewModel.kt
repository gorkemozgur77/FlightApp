package com.gorkemozgur.flightapp.module.home_page.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gorkemozgur.flightapp.BaseViewModel
import com.gorkemozgur.flightapp.database.FlightAppDatabase
import com.gorkemozgur.flightapp.model.Resource
import com.gorkemozgur.flightapp.model.flight.Flight
import com.gorkemozgur.flightapp.model.flight.FlightResponseModel
import com.gorkemozgur.flightapp.service.ApiClient
import com.gorkemozgur.flightapp.util.Constants
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FlightsViewModel(application: Application) : BaseViewModel(application) {

    private var offset = 0
    private var limit = 15

    private val _flightData = MutableLiveData<Resource<List<Flight>>>()
    val flightData: LiveData<Resource<List<Flight>>>
        get() = _flightData

    fun sendRequest() {
        _flightData.postValue(Resource.loading())
        ApiClient().getAuthApiService().getFlights(Constants.API_ACCESS_KEY, limit, offset)
            .enqueue(object :
                Callback<FlightResponseModel> {
                override fun onResponse(
                    call: Call<FlightResponseModel>,
                    response: Response<FlightResponseModel>
                ) {
                    println(response.body().toString())
                    if (response.isSuccessful) {
                        response.body()?.let {
                            _flightData.postValue(Resource.success(it.flight))
                            saveToDatabase(it.flight)
                        }


                        offset += limit
                    } else
                        _flightData.postValue(Resource.error(response.errorBody()?.string()))

                }

                override fun onFailure(call: Call<FlightResponseModel>, t: Throwable) {
                    _flightData.postValue(Resource.error(t.message))
                    println(t.localizedMessage)
                }

            })
    }

    private fun saveToDatabase(list: List<Flight>) {
        launch {
            val dao = FlightAppDatabase(getApplication()).getDao()
            dao.upsertAllFlights(*list.toTypedArray())
        }
    }

    fun getFromDatabase() {
        launch {
            val dao = FlightAppDatabase(getApplication()).getDao()
            val flights = dao.getAllFlights()
            _flightData.postValue(Resource.success(flights))
        }

    }


}