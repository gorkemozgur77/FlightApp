package com.gorkemozgur.flightapp.module.home_page.viewmodel

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.gorkemozgur.flightapp.BaseViewmodel
import com.gorkemozgur.flightapp.model.airpot_request.Airport
import com.gorkemozgur.flightapp.model.airpot_request.AirportResponseModel
import com.gorkemozgur.flightapp.service.ApiClient
import com.gorkemozgur.flightapp.util.Constants
import retrofit2.Call
import retrofit2.Response

class AirportsViewModel(application: Application): BaseViewmodel(application) {

    val airports = MutableLiveData<List<Airport>>()
    var offset = 0
    var limit = 15

     fun sendRequest(){
        val client = ApiClient()
        client.getAuthApiService().getAirports(Constants.API_ACCESS_KEY, limit, offset).enqueue(
            object : retrofit2.Callback<AirportResponseModel>{
                override fun onResponse(
                    call: Call<AirportResponseModel>,
                    response: Response<AirportResponseModel>
                ) {
                    if (response.isSuccessful){
                        println(response.body())
                        response.body()?.let { airports.value = it.airport }
                        offset += limit
                    }
                    else
                        println(response.errorBody()?.string())
                }
                override fun onFailure(call: Call<AirportResponseModel>, t: Throwable) {

                }
            }
        )
    }
}
