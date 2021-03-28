package com.gorkemozgur.flightapp.service

import com.gorkemozgur.flightapp.model.airpot_request.AirportResponseModel
import com.gorkemozgur.flightapp.model.flight.FlightResponseModel
import com.gorkemozgur.flightapp.util.Constants.GET_AIRPORTS_URL
import com.gorkemozgur.flightapp.util.Constants.GET_FLIGHT_URL
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(GET_AIRPORTS_URL)
    fun getAirports(
            @Query("access_key")
            accessKey: String,

            @Query("limit")
            limit: Int,

            @Query("offset")
            offset: Int
    ): Call<AirportResponseModel>

    @GET(GET_FLIGHT_URL)
    fun getFlights(
            @Query("access_key")
            accessKey: String,

            @Query("limit")
            limit: Int,

            @Query("offset")
            offset: Int
    ): Call<FlightResponseModel>

}