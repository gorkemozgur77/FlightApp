package com.gorkemozgur.flightapp.module.home_page.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gorkemozgur.flightapp.BaseViewModel
import com.gorkemozgur.flightapp.database.FlightAppDatabase
import com.gorkemozgur.flightapp.model.Resource
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import com.gorkemozgur.flightapp.model.airpot_request.AirportResponseModel
import com.gorkemozgur.flightapp.service.ApiClient
import com.gorkemozgur.flightapp.util.Constants
import com.gorkemozgur.flightapp.util.CustomSharedPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response

class AirportsViewModel(application: Application) : BaseViewModel(application) {

    companion object {
        const val AIRPORT = "airport"
    }

    var offset = 0
    var limit = 15

    private val _airportsData = MutableLiveData<Resource<List<Airport>>>()
    val airportsData: LiveData<Resource<List<Airport>>>
        get() = _airportsData

    private val customSharedPreferences = CustomSharedPreferences(getApplication())
    private val dao = FlightAppDatabase(getApplication()).getDao()

    fun refreshData() {

        val savedTime = customSharedPreferences.getTime(AIRPORT)
        if (savedTime != null && savedTime != 0L && System.nanoTime() - savedTime < super.dataUpdateTime) {
            getFromDatabase()
        } else {
            launch {
                dao.deleteAllAirports()
            }
            sendRequest()
        }
    }

    fun sendRequest() {
        _airportsData.postValue(Resource.loading())
        ApiClient().getAuthApiService().getAirports(Constants.API_ACCESS_KEY, limit, offset)
            .enqueue(
                object : retrofit2.Callback<AirportResponseModel> {
                    override fun onResponse(
                        call: Call<AirportResponseModel>,
                        response: Response<AirportResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            println(response.body())
                            response.body()?.let {
                                it.airport.forEach { it1 ->
                                    it1.offset = offset
                                }
                                _airportsData.postValue(Resource.success(it.airport))
                                saveToDatabase(it.airport)
                                Toast.makeText(
                                    getApplication(),
                                    "Besinleri Internet'ten Aldık",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                            offset += limit

                        } else
                            _airportsData.postValue(Resource.error(response.errorBody()?.string()))
                    }

                    override fun onFailure(call: Call<AirportResponseModel>, t: Throwable) {

                        _airportsData.postValue(Resource.error(t.message))

                    }
                }
            )
    }

    private fun saveToDatabase(list: List<Airport>) {
        launch {
            dao.upsertAllAirports(*list.toTypedArray())
        }
        customSharedPreferences.saveTime(System.nanoTime(), AIRPORT)
    }

    fun getFromDatabase() {
        launch {

            val airports = dao.getAllAirports()
            _airportsData.postValue(Resource.success(airports))
            offset = airports.last().offset + limit
        }
        Toast.makeText(
            getApplication(),
            "Besinleri database'ten Aldık",
            Toast.LENGTH_LONG
        ).show()

    }
}
