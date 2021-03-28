package com.gorkemozgur.flightapp.module.home_page.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.model.Resource
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import com.gorkemozgur.flightapp.model.airpot_request.AirportResponseModel
import com.gorkemozgur.flightapp.service.ApiClient
import com.gorkemozgur.flightapp.util.Constants
import retrofit2.Call
import retrofit2.Response

class AirportsViewModel(application: Application) : BaseViewmodel(application) {

    private var offset = 0
    private var limit = 15

    private val _airportsData = MutableLiveData<Resource<List<Airport>>>()
    val airportsData: LiveData<Resource<List<Airport>>>
        get() = _airportsData

    fun sendRequest() {
        _airportsData.postValue(Resource.loading())
        ApiClient().getAuthApiService().getAirports(Constants.API_ACCESS_KEY, limit, offset).enqueue(
                object : retrofit2.Callback<AirportResponseModel> {
                    override fun onResponse(
                            call: Call<AirportResponseModel>,
                            response: Response<AirportResponseModel>
                    ) {
                        if (response.isSuccessful) {
                            println(response.body())
                            response.body()?.let {
                             _airportsData.postValue(Resource.success(it.airport))
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
}
